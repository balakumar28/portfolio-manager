package db

import (
	"database/sql"
	_ "github.com/joho/godotenv/autoload"
	_ "github.com/lib/pq"
	"io/ioutil"
	"log"
	"os"
)

var db *sql.DB

// Init exposed to init for test
func Init() {
	var err error
	db, err = sql.Open("postgres", os.Getenv("DB_URL"))
	if err != nil {
		panic(err)
	}

	err = db.Ping()
	if err != nil {
		panic(err)
	}
	log.Println("DB Connected!")
	err = createSchema()
	if err != nil {
		panic(err)
	}
	log.Println("Schema Created!")
}

func createSchema() error {
	file, err := ioutil.ReadFile(os.Getenv("DB_SCHEMA"))
	if err != nil {
		return err
	}
	_, err = db.Exec(string(file))
	return err
}
