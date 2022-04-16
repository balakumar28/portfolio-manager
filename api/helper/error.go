package helper

import (
	"github.com/gin-gonic/gin"
	"log"
	"net/http"
)

func ErrorToJson(err error) gin.H {
	return gin.H{"err": err.Error()}
}

func RequestError(ctx *gin.Context, code int, err error) {
	if err != nil {
		log.Println(err)
		ctx.JSON(code, ErrorToJson(err))
	} else {
		ctx.Status(code)
	}
	ctx.Abort()
}

func FailRequest(ctx *gin.Context, err error) {
	RequestError(ctx, http.StatusBadRequest, err)
}