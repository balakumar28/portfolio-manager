package test

import (
	"bytes"
	"github.com/balakumar28/portfolio-manager/api/db"
	"github.com/balakumar28/portfolio-manager/api/route"
	embeddedpostgres "github.com/fergusstrange/embedded-postgres"
	"github.com/gin-gonic/gin"
	"github.com/stretchr/testify/assert"
	"io"
	"log"
	"net/http"
	"net/http/httptest"
	"os"
	"testing"

	_ "github.com/balakumar28/portfolio-manager/api/label"
	_ "github.com/balakumar28/portfolio-manager/api/tx"
	_ "github.com/balakumar28/portfolio-manager/api/user"
)

var pg *embeddedpostgres.EmbeddedPostgres

func setup() {
	_ = os.Setenv("DB_URL", "host=localhost port=5432 user=postgres password=postgres database=postgres sslmode=disable")
	_ = os.Setenv("DB_SCHEMA", "../../schema/postgres.sql")
	pg = embeddedpostgres.NewDatabase()
	err := pg.Start()
	if err != nil {
		log.Println(err)
	}
	db.Init()
}

func shutdown() {
	if pg != nil {
		err := pg.Stop()
		if err != nil {
			log.Println(err)
		}
	}
}

func TestMain(m *testing.M) {
	setup()
	code := m.Run()
	shutdown()
	os.Exit(code)
}

// Helper function to process a request and test its response
func testHTTPResponse(t *testing.T, r *gin.Engine, req *http.Request, status int) *http.Response {

	// Create a response recorder
	w := httptest.NewRecorder()

	// Create the service and process the above request.
	r.ServeHTTP(w, req)

	if w.Body != nil {
		b, err := io.ReadAll(w.Body)
		if err != nil {
			log.Println(err)
		}
		if len(b) > 0 {
			log.Println(string(b))
		}
	}
	assert.Equal(t, status, w.Code)

	return w.Result()
}

func Test_WithoutAuth(t *testing.T) {
	log.Println("Test_WithoutAuth")
	r := route.Setup()

	req, err := http.NewRequest(http.MethodGet, "/session/user", nil)
	assert.Nil(t, err)
	testHTTPResponse(t, r, req, http.StatusUnauthorized)

	req, err = http.NewRequest(http.MethodPost, "/label/create", nil)
	assert.Nil(t, err)
	testHTTPResponse(t, r, req, http.StatusUnauthorized)

}

func Test_WithAuth(t *testing.T) {
	log.Println("Test_WithAuth")
	r := route.Setup()

	req, err := http.NewRequest(http.MethodPost, "/user/create", bytes.NewBuffer([]byte(`{"Name": "bala", "Password": "bala"}`)))
	assert.Nil(t, err)
	testHTTPResponse(t, r, req, http.StatusCreated)

	req, err = http.NewRequest(http.MethodPost, "/login", bytes.NewBuffer([]byte(`{"Name": "bala", "Password": "bala1"}`)))
	assert.Nil(t, err)
	testHTTPResponse(t, r, req, http.StatusUnauthorized)

	req, err = http.NewRequest(http.MethodPost, "/login", bytes.NewBuffer([]byte(`{"Name": "bala"}`)))
	assert.Nil(t, err)
	testHTTPResponse(t, r, req, http.StatusBadRequest)

	req, err = http.NewRequest(http.MethodPost, "/login", nil)
	assert.Nil(t, err)
	testHTTPResponse(t, r, req, http.StatusBadRequest)


	req, err = http.NewRequest(http.MethodPost, "/login", bytes.NewBuffer([]byte(`{"Name": "bala", "Password": "bala"}`)))
	assert.Nil(t, err)
	response := testHTTPResponse(t, r, req, http.StatusOK)

	req, err = http.NewRequest(http.MethodGet, "/session/user", nil)
	for _, c := range response.Cookies() {
		req.AddCookie(c)
	}
	assert.Nil(t, err)
	testHTTPResponse(t, r, req, http.StatusOK)

	req, err = http.NewRequest(http.MethodPost, "/label/create", bytes.NewBuffer([]byte(`{"Name": "default"}`)))
	assert.Nil(t, err)
	for _, c := range response.Cookies() {
		req.AddCookie(c)
	}
	testHTTPResponse(t, r, req, http.StatusCreated)

	instrument := &db.Instrument{Exchange: "NSE", Symbol: "TCS"}
	assert.Nil(t, instrument.Save())

	req, err = http.NewRequest(http.MethodPost, "/user/transaction", bytes.NewBuffer([]byte(`{"Label": "default", "Instrument": "NSE.TCS", "Date": "2022-04-15T09:18:57Z", "Type": 1, "Qty": 10, "Price": 3696.45}`)))
	assert.Nil(t, err)
	for _, c := range response.Cookies() {
		req.AddCookie(c)
	}
	testHTTPResponse(t, r, req, http.StatusOK)

	req, err = http.NewRequest(http.MethodPost, "/user/transaction", bytes.NewBuffer([]byte(`{"Label": "default", "Instrument": "NSE.TCS", "Date": "2022-04-16T09:35:13Z", "Type": 1, "Qty": 3, "Price": 3570}`)))
	assert.Nil(t, err)
	for _, c := range response.Cookies() {
		req.AddCookie(c)
	}
	testHTTPResponse(t, r, req, http.StatusOK)

	req, err = http.NewRequest(http.MethodPost, "/label/snapshot", bytes.NewBuffer([]byte(`{"Name": "default"}`)))
	assert.Nil(t, err)
	for _, c := range response.Cookies() {
		req.AddCookie(c)
	}
	testHTTPResponse(t, r, req, http.StatusOK)

	req, err = http.NewRequest(http.MethodPost, "/logout", nil)
	assert.Nil(t, err)
	for _, c := range response.Cookies() {
		req.AddCookie(c)
	}
	testHTTPResponse(t, r, req, http.StatusOK)
}
