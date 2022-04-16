package db

import (
	"fmt"
)

type AlreadyExist struct {
	Obj interface{}
}

func (e AlreadyExist) Error() string {
	return fmt.Sprint(e.Obj, " does not exist")
}

type NotExist struct {
	Obj interface{}
}

func (e NotExist) Error() string {
	return fmt.Sprint(e.Obj, " does not exist")
}
