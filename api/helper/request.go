package helper

import (
	"fmt"
	"github.com/gin-gonic/gin"
)

func ReadRequest(ctx *gin.Context, i interface{}) bool {
	err := ctx.BindJSON(i)
	if err != nil {
		FailRequest(ctx, fmt.Errorf("failed to read request %w", err))
		return false
	}
	return true
}
