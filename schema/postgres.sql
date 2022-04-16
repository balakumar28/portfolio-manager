CREATE TABLE IF NOT EXISTS Users (
	Id serial NOT NULL,
	Name TEXT,
	Password character varying(256) NOT NULL,
	CONSTRAINT Users_pk PRIMARY KEY (Id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE IF NOT EXISTS Label (
	Id serial NOT NULL,
	UserId serial NOT NULL,
	Name character varying(20) NOT NULL,
	Data TEXT,
	CONSTRAINT Label_pk PRIMARY KEY (Id),
	UNIQUE (UserId, Name)
) WITH (
  OIDS=FALSE
);



CREATE TABLE IF NOT EXISTS Transaction (
	Id serial NOT NULL,
	LabelId bigint NOT NULL,
	InstrumentId bigint NOT NULL,
	Date TIMESTAMP NOT NULL,
	Type int NOT NULL,
	Quantity real,
	Price real,
	Value real,
	CONSTRAINT Transaction_pk PRIMARY KEY (Id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE IF NOT EXISTS Instrument (
	Id serial NOT NULL,
	Exchange character varying(50) NOT NULL,
	Symbol character varying(50) NOT NULL UNIQUE,
	Description TEXT,
	CONSTRAINT Instrument_pk PRIMARY KEY (Id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE IF NOT EXISTS Snapshot (
	Id serial NOT NULL,
	LabelId bigint NOT NULL,
	InstrumentId bigint NOT NULL,
	Quantity real NOT NULL,
	Price real NOT NULL,
	CONSTRAINT Snapshot_pk PRIMARY KEY (Id)
) WITH (
  OIDS=FALSE
);



ALTER TABLE Label DROP CONSTRAINT IF EXISTS Label_fk0;
ALTER TABLE Label ADD CONSTRAINT Label_fk0 FOREIGN KEY (UserId) REFERENCES Users(Id);

ALTER TABLE Transaction DROP CONSTRAINT IF EXISTS Transaction_fk0;
ALTER TABLE Transaction DROP CONSTRAINT IF EXISTS Transaction_fk1;
ALTER TABLE Transaction ADD CONSTRAINT Transaction_fk0 FOREIGN KEY (LabelId) REFERENCES Label(Id);
ALTER TABLE Transaction ADD CONSTRAINT Transaction_fk1 FOREIGN KEY (InstrumentId) REFERENCES Instrument(Id);

ALTER TABLE Snapshot DROP CONSTRAINT IF EXISTS Snapshot_fk0;
ALTER TABLE Snapshot DROP CONSTRAINT IF EXISTS Snapshot_fk1;
ALTER TABLE Snapshot ADD CONSTRAINT Snapshot_fk0 FOREIGN KEY (LabelId) REFERENCES Label(Id);
ALTER TABLE Snapshot ADD CONSTRAINT Snapshot_fk1 FOREIGN KEY (InstrumentId) REFERENCES Instrument(Id);





