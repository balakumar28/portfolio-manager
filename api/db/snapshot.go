package db

const (
	insertSnapshot                    = "insert into snapshot (labelid, instrumentid, quantity, price) values ($1, $2, $3, $4) returning id"
	updateSnapshot                    = "update snapshot set quantity = $2, price = $3 where id = $1"
	querySnapshotByLabel              = "select id, labelid, instrumentid, quantity, price from snapshot where labelid = $1"
	querySnapshotByLabelAndInstrument = "select id, labelid, instrumentid, quantity, price from snapshot where labelid = $1 and instrumentid = $2"
)

type Snapshot struct {
	Id         int
	Label      Label
	Instrument Instrument
	Quantity   float64
	Price      float64
}
