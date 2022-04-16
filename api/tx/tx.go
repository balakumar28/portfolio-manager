package tx

import (
	"fmt"
	"github.com/balakumar28/portfolio-manager/api/helper"
	"github.com/balakumar28/portfolio-manager/api/request"
	"github.com/balakumar28/portfolio-manager/api/session"
	"github.com/gin-gonic/gin"
	"net/http"
)

func newTx(ctx *gin.Context) {
	user, ok := session.GetUser(ctx)
	if !ok {
		return
	}

	var req request.Transaction
	if !helper.ReadRequest(ctx, &req) {
		return
	}

	if !req.Validate() {
		helper.FailRequest(ctx, fmt.Errorf("invalid req %+v", req))
		return
	}

	label, err := user.GetLabel(req.Label)
	if err != nil {
		helper.RequestError(ctx, http.StatusInternalServerError, err)
		return
	}

	transaction, err := label.NewTransaction(req)
	if err != nil {
		helper.RequestError(ctx, http.StatusInternalServerError, err)
		return
	}
	err = transaction.Save()
	if err != nil {
		helper.RequestError(ctx, http.StatusInternalServerError, err)
		return
	}
	ctx.Status(http.StatusOK)
}
