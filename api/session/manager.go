package session

import (
	"fmt"
	"github.com/balakumar28/portfolio-manager/api/db"
	"github.com/balakumar28/portfolio-manager/api/helper"
	"github.com/gin-contrib/sessions"
	"github.com/gin-gonic/gin"
	"log"
	"net/http"
)

func Invalidate(ctx *gin.Context) error {
	session := sessions.Default(ctx)
	session.Clear()
	return session.Save()
}

func InvalidateSilently(ctx *gin.Context) {
	err := Invalidate(ctx)
	if err != nil {
		log.Println("Failed to invalidate session", err)
	}
}

func GetUser(ctx *gin.Context) (*db.User, bool) {
	id := sessions.Default(ctx).Get("ID")
	if id == nil {
		helper.RequestError(ctx, http.StatusUnauthorized, nil)
		return nil, false
	}
	user, err := db.GetUserById(id.(int))
	if err != nil {
		InvalidateSilently(ctx)
		helper.FailRequest(ctx, fmt.Errorf("failed to retrieve user: %w", err))
		return nil, false
	}
	return user, true
}