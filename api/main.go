package main

import (
	"github.com/balakumar28/portfolio-manager/api/route"
	"log"

	_ "github.com/balakumar28/portfolio-manager/api/label"
	_ "github.com/balakumar28/portfolio-manager/api/tx"
	_ "github.com/balakumar28/portfolio-manager/api/user"
)

func main() {
	log.Fatalln(route.Setup().Run())
}
