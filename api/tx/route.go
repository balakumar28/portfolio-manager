package tx

import (
	"github.com/balakumar28/portfolio-manager/api/route"
	"net/http"
)

func init() {
	route.Register(http.MethodPost, "/user/transaction", newTx)
}

