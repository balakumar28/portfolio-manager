package label

import (
	"github.com/balakumar28/portfolio-manager/api/route"
	"net/http"
)

func init() {
	route.Register(http.MethodPost, "/label/create", createLabel)
	route.Register(http.MethodPost, "/label/snapshot", getSnapshot)
}

