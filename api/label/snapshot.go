package label

import (
	"errors"
	"fmt"
	"github.com/balakumar28/portfolio-manager/api/helper"
	"github.com/balakumar28/portfolio-manager/api/request"
	"github.com/balakumar28/portfolio-manager/api/session"
	"github.com/gin-gonic/gin"
	"net/http"
)

func getSnapshot(ctx *gin.Context) {
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

	label, err := user.GetLabel(req.Name)
	if err != nil {
		helper.RequestError(ctx, http.StatusBadRequest, fmt.Errorf("failed to read label: %w", err))
		return
	}

	snapshot, err := label.Snapshot()
	if err != nil {
		helper.RequestError(ctx, http.StatusBadRequest, fmt.Errorf("failed to fetch snapshot: %w", err))
		return
	}

	ctx.JSON(http.StatusOK, snapshot)
}
