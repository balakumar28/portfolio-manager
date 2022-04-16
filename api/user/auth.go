package user

import (
	"errors"
	"fmt"
	"github.com/balakumar28/portfolio-manager/api/db"
	"github.com/balakumar28/portfolio-manager/api/helper"
	"github.com/balakumar28/portfolio-manager/api/request"
	"github.com/balakumar28/portfolio-manager/api/session"
	"github.com/gin-contrib/sessions"
	"github.com/gin-gonic/gin"
	"log"
	"net/http"
)

type AuthStatus int

const (
	Success AuthStatus = iota
	InvalidUser
	InvalidPassword
)

func (s AuthStatus) String() string {
	return []string{"Success", "InvalidUser", "InvalidPassword"}[s]
}

func login(ctx *gin.Context) {
	var userReq request.User
	if !helper.ReadRequest(ctx, &userReq) {
		return
	}

	if len(userReq.Name) == 0 || len(userReq.Password) == 0 {
		helper.FailRequest(ctx, fmt.Errorf("invalid req: %+v", userReq))
		return
	}

	u, status := Auth(userReq.Name, userReq.Password)
	if status == Success {
		session := sessions.Default(ctx)
		session.Set("ID", u.Id)
		err := session.Save()
		if err != nil {
			helper.RequestError(ctx, http.StatusInternalServerError, err)
			return
		}
		ctx.Status(http.StatusOK)
	} else {
		helper.RequestError(ctx, http.StatusUnauthorized, errors.New(status.String()))
	}
}

func Auth(user, pass string) (*db.User, AuthStatus) {
	u, err := db.GetUser(user)
	if err != nil {
		log.Println(err)
		return u, InvalidUser
	}

	if u.Password == pass {
		return u, Success
	} else {
		return u, InvalidPassword
	}
}

func validate(ctx *gin.Context) {
	user, ok := session.GetUser(ctx)
	if !ok {
		return
	}
	ctx.JSON(http.StatusOK, user)
}


func logout(ctx *gin.Context) {
	err := session.Invalidate(ctx)
	if err != nil {
		helper.RequestError(ctx, http.StatusInternalServerError, err)
	}
	ctx.Status(http.StatusOK)
}