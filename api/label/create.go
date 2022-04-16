package label

import (
	"errors"
	"fmt"
	"github.com/balakumar28/portfolio-manager/api/db"
	"github.com/balakumar28/portfolio-manager/api/helper"
	"github.com/balakumar28/portfolio-manager/api/request"
	"github.com/balakumar28/portfolio-manager/api/session"
	"github.com/gin-gonic/gin"
	"net/http"
)

func createLabel(ctx *gin.Context) {
	user, ok := session.GetUser(ctx)
	if !ok {
		return
	}

	var req request.Label
	if !helper.ReadRequest(ctx, &req) {
		return
	}

	if len(req.Name) == 0 {
		helper.FailRequest(ctx, errors.New("missing label name"))
		return
	}

	label := user.NewLabel(req)
	err := label.Save()
	if err != nil {
		if errors.Is(err, db.AlreadyExist{}) {
			helper.RequestError(ctx, http.StatusBadRequest, fmt.Errorf("failed to save label: %w", err))
		} else {
			helper.RequestError(ctx, http.StatusInternalServerError, fmt.Errorf("failed to save label: %w", err))
		}
		return
	}
	ctx.Status(http.StatusCreated)
}
