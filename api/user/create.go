package user

import (
	"fmt"
	"github.com/balakumar28/portfolio-manager/api/db"
	"github.com/balakumar28/portfolio-manager/api/helper"
	"github.com/balakumar28/portfolio-manager/api/request"
	"github.com/gin-gonic/gin"
	"net/http"
)

func createUser(ctx *gin.Context) {
	var req request.User
	if !helper.ReadRequest(ctx, &req) {
		return
	}

	if len(req.Name) == 0 || len(req.Password) == 0 {
		helper.FailRequest(ctx, fmt.Errorf("invalid req: %v", req))
		return
	}
	user := db.User{
		Name:     req.Name,
		Password: req.Password,
	}
	err := user.Save()
	if err != nil {
		helper.RequestError(ctx, http.StatusInternalServerError, err)
		return
	}
	ctx.JSON(http.StatusCreated, user)
}