CREATE TABLE users (
   userId SERIAL,
   firstName VARCHAR(50) NOT NULL, 
   lastName VARCHAR(50) NOT NULL,
   username VARCHAR(50) UNIQUE NOT NULL, 
   userPswd VARCHAR(50) NOT NULL, 
   userRole VARCHAR(10) NOT NULL, 
   CONSTRAINT pkUserId PRIMARY KEY (userId),
   CONSTRAINT chkUserRole CHECK (userRole IN ('racunovoda', 'direktor', 'zaposlenik', 'revizor'))
);

CREATE TABLE scanHistory (
   docId SERIAL,
   userId INT NOT NULL,
   scanDate DATE NOT NULL,
   CONSTRAINT pkDocIdScan PRIMARY KEY (docId),
   CONSTRAINT fkUserIdScan FOREIGN KEY (userId) REFERENCES users(userId)
      ON DELETE CASCADE
);

CREATE TABLE documents (
   docId INT,
   docLabel VARCHAR(10) UNIQUE,
   docType VARCHAR(1),
   docText TEXT,
   CONSTRAINT pkDocId PRIMARY KEY (docId),
   CONSTRAINT fkDocIdScan FOREIGN KEY (docId) REFERENCES scanHistory(docId)
      ON DELETE CASCADE,
   CONSTRAINT chkDocType CHECK (docType IN ('R', 'P', 'I', NULL))
);

CREATE TABLE accountantResponsibility (
   userId INT, 
   responsibility VARCHAR(1) NOT NULL, 
   CONSTRAINT pkAccResponsibility PRIMARY KEY (userId), 
   CONSTRAINT fkAccountantResponsibilityUsers FOREIGN KEY (userId) REFERENCES users(userId)
      ON DELETE CASCADE,
   CONSTRAINT chkResponsibility CHECK (responsibility IN ('R', 'P', 'I'))
);

CREATE TABLE archive (
   archiveId SERIAL,
   docId INT NOT NULL,
   userId INT NOT NULL,
   archivedDate DATE NOT NULL,
   signed SMALLINT NOT NULL,
   CONSTRAINT pkArchivedId PRIMARY KEY (archiveId),
   CONSTRAINT fkDocIdArchive FOREIGN KEY (docId) REFERENCES documents(docId)
      ON DELETE CASCADE,
   CONSTRAINT fkUserIdArchive FOREIGN KEY (userId) REFERENCES users(userId)
      ON DELETE CASCADE,
   CONSTRAINT chkSigned CHECK (signed BETWEEN 0 AND 1)
);

CREATE TABLE reviserPending (
   docId INT,
   userId INT NOT NULL,
   CONSTRAINT pkReviserPendingDocId PRIMARY KEY (docId),
   CONSTRAINT fkReviserPendingDocId FOREIGN KEY (docId) REFERENCES documents(docId)
      ON DELETE CASCADE,
   CONSTRAINT fkReviserPendingUserId FOREIGN KEY (userId) REFERENCES users(userId)
      ON DELETE CASCADE
);

CREATE TABLE executivePending (
   docId INT,
   userId INT NOT NULL,
   CONSTRAINT pkExecutivePendingDocId PRIMARY KEY (docId),
   CONSTRAINT fkExecutivePendingDocId FOREIGN KEY (docId) REFERENCES documents(docId)
      ON DELETE CASCADE,
   CONSTRAINT fkExecutivePendingUserId FOREIGN KEY (userId) REFERENCES users(userId)
      ON DELETE CASCADE
);

CREATE TABLE accountantPending (
   docId INT,
   userId INT NOT NULL,
   signaturePending SMALLINT NOT NULL,
   CONSTRAINT pkAccountantPendingDocId PRIMARY KEY (docId),
   CONSTRAINT fkAccountantPendingDocId FOREIGN KEY (docId) REFERENCES documents(docId)
      ON DELETE CASCADE,
   CONSTRAINT fkAccountantPendingUserId FOREIGN KEY (userId) REFERENCES users(userId)
      ON DELETE CASCADE,
   CONSTRAINT chksignaturePending CHECK (signaturePending BETWEEN 0 AND 2)
);
