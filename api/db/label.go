package db

import (
	"github.com/balakumar28/portfolio-manager/api/request"
)

const (
	insertLabel = "insert into label (userid, name, data) values ($1, $2, $3) returning id"
	queryLabelByUserAndName = "select id, userid, name, data from label where userid = $1 and name = $2"
)

type Label struct {
	Id     int
	UserId int
	Name   string
	Data   string
}

func (l *Label) Save() error {
	return db.QueryRow(insertLabel, l.UserId, l.Name, l.Data).Scan(&l.Id)
}

func (l *Label) NewTransaction(tx request.Transaction) (*Transaction, error) {
	instrumentStr := InstrumentStr(tx.Instrument)
	instrument, err := GetInstrument(instrumentStr.Exchange(), instrumentStr.Symbol())
	if err != nil {
		return nil, err
	}
	return &Transaction{
		LabelId:      l.Id,
		InstrumentId: instrument.Id,
		Date:         tx.Date,
		Type:         int(tx.Type),
		Quantity:     tx.Qty,
		Price:        tx.Price,
		Value:        tx.Value,
	}, nil
}

func (l *Label) Snapshot() (*Snapshot, error) {
	var s Snapshot
	s.Label = *l
	err := db.QueryRow(querySnapshotByLabel, l.Id).Scan(&s.Id, &s.Label.Id, &s.Instrument.Id, &s.Quantity, &s.Price)
	if err != nil {
		return nil, err
	}
	err = db.QueryRow(queryInstrumentById, s.Instrument.Id).Scan(&s.Instrument.Exchange, &s.Instrument.Symbol, &s.Instrument.Description)
	return &s, err
}