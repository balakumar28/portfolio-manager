package db

import (
	"database/sql"
	"time"
)

const (
	insertTx = "insert into transaction (labelid, instrumentid, date, type, quantity, price, value) values ($1, $2, $3, $4, $5, $6, $7) returning id"
)

type Transaction struct {
	Id           int
	LabelId      int
	InstrumentId int
	Date         time.Time
	Type         int
	Quantity     float64
	Price        float64
	Value        float64
}

func (t *Transaction) Save() error {
	err := db.QueryRow(insertTx, t.LabelId, t.InstrumentId, t.Date, t.Type, t.Quantity, t.Price, t.Value).Scan(&t.Id)
	if err != nil {
		return err
	}
	_, err = t.UpdateSnapshot()
	if err != nil {
		return err
	}
	return nil
}

func (t *Transaction) UpdateSnapshot() (*Snapshot, error) {
	var s Snapshot
	err := db.QueryRow(querySnapshotByLabelAndInstrument, t.LabelId, t.InstrumentId).Scan(&s.Id, &s.Label.Id, &s.Instrument.Id, &s.Quantity, &s.Price)
	if err == sql.ErrNoRows {
		s.Label.Id = t.LabelId
		s.Instrument.Id = t.InstrumentId
		s.Quantity = t.Quantity
		s.Price = t.Price
		err = db.QueryRow(insertSnapshot, t.LabelId, t.InstrumentId, t.Quantity, t.Price).Scan(&s.Id)
	} else if err == nil {
		newQty := t.Quantity + s.Quantity
		s.Price = ((t.Quantity * t.Price) + (s.Quantity * s.Price)) / newQty
		s.Quantity = newQty
		_, err = db.Exec(updateSnapshot, s.Id, s.Quantity, s.Price)
	}
	return &s, err
}