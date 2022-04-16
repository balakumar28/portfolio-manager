package user

import (
	"github.com/balakumar28/portfolio-manager/api/route"
	"net/http"
)

func init() {
	route.Register(http.MethodPost, "/user/create", createUser)
	route.Register(http.MethodGet, "/session/user", validate)
	route.Register(http.MethodPost, "/login", login)
	route.Register(http.MethodPost, "/logout", logout)
}

