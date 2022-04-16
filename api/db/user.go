package db

import (
	"github.com/balakumar28/portfolio-manager/api/request"
)

const (
	insertUser = "insert into users (name, password) values ($1, $2) returning id"
	queryUserById = "select id, name, password from users where id = $1"
	queryUserByName = "select id, name, password from users where name = $1"
)

type User struct {
	Id   int
	Name string
	Password string
}

func (u *User) Save() error {
	row := db.QueryRow(insertUser, u.Name, u.Password)
	if row.Err() != nil {
		return row.Err()
	}
	return row.Scan(&u.Id)
}

func (u *User) NewLabel(label request.Label) *Label {
	return &Label{
		UserId: u.Id,
		Name:   label.Name,
	}
}

func (u *User) GetLabel(label string) (*Label, error) {
	var l Label
	err := db.QueryRow(queryLabelByUserAndName, u.Id, label).Scan(&l.Id, &l.UserId, &l.Name, &l.Data)
	return &l, err
}

func GetUser(name string) (*User, error) {
	var u User
	err := db.QueryRow(queryUserByName, name).Scan(&u.Id, &u.Name, &u.Password)
	return &u, err
}

func GetUserById(id int)  (*User, error) {
	var u User
	err := db.QueryRow(queryUserById, id).Scan(&u.Id, &u.Name, &u.Password)
	return &u, err
}
