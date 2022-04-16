package route

import (
	"github.com/gin-contrib/sessions"
	"github.com/gin-contrib/sessions/cookie"
	"github.com/gin-gonic/gin"
)

type route struct {
	method   string
	path     string
	handlers []gin.HandlerFunc
}

var routes []route

func Register(httpMethod, relativePath string, handlers ...gin.HandlerFunc) {
	routes = append(routes, route{
		method:   httpMethod,
		path:     relativePath,
		handlers: handlers,
	})
}

func Setup() *gin.Engine {
	r := gin.Default()
	r.Use(sessions.Sessions("portfolio-manager", cookie.NewStore([]byte("secret"))))
	r.GET("/ping")
	for _, route := range routes {
		r.Handle(route.method, route.path, route.handlers...)
	}
	return r
}
