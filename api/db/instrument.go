package db

import "strings"

const (
	insertInstrument                   = "insert into instrument (exchange, symbol, description) values ($1, $2, $3) returning id"
	queryInstrumentById                = "select exchange, symbol, description from instrument where id = $1"
	queryInstrumentByExchangeAndSymbol = "select id, exchange, symbol, description from instrument where exchange = $1 and symbol = $2"
)

type InstrumentStr string

func (s InstrumentStr) Exchange() string {
	return strings.SplitN(string(s), ".", 2)[0]
}

func (s InstrumentStr) Symbol() string {
	return strings.SplitN(string(s), ".", 2)[1]
}

type Instrument struct {
	Id          int
	Exchange    string
	Symbol      string
	Description string
}

func (i *Instrument) Save() error {
	return db.QueryRow(insertInstrument, i.Exchange, i.Symbol, i.Description).Scan(&i.Id)
}

func GetInstrument(exchange string, symbol string) (*Instrument, error) {
	var i Instrument
	err := db.QueryRow(queryInstrumentByExchangeAndSymbol, exchange, symbol).Scan(&i.Id, &i.Exchange, &i.Symbol, &i.Description)
	return &i, err
}
