package request

import "time"

type TxType int

const (
	Buy TxType = iota + 1
	Sell
)

type Transaction struct {
	Label      string
	Instrument string // exchange.symbol
	Date       time.Time
	Type       TxType
	Qty        float64
	Price      float64
	Value      float64
}

func (t *Transaction) Validate() bool {
	t.Value = t.Qty * t.Price
	return len(t.Label) != 0 && len(t.Instrument) != 0 && !t.Date.IsZero() && t.Type != 0 && t.Qty != 0 && t.Price != 0
}
