import psycopg2
import psycopg2.extras
import json



def archive_handler(event, context):

    connection=psycopg2.connect(
        database="IS2_Digitalizacija",
        user="postgres",
        password="bazepodataka",
        host="is2digitalizacijav2.cwck16atirhq.eu-central-1.rds.amazonaws.com"
    )

    cur=connection.cursor(cursor_factory = psycopg2.extras.RealDictCursor)
    
    docID=event["docid"]
    accountantID=event["userid"]
    archiveDate=event["archivedate"]

    cur.execute("select * from accountantpending where docid='{0}'"
    .format(docID))

    dokument=cur.fetchone()

    potpis=dokument["signaturepending"]

    if str(potpis)=="2":
        cur.execute("insert into archive(docId, userId, archivedDate, signed) values ('{0}','{1}','{2}',1)"
        .format(docID,accountantID,archiveDate))
        connection.commit()
    else:
        cur.execute("insert into archive(docId, userId, archivedDate, signed) values ('{0}','{1}','{2}',0)"
        .format(docID,accountantID,archiveDate))
        connection.commit()

    cur.execute("delete from accountantpending where docid='{0}'"
    .format(docID))
    connection.commit()
    
    return "uspjeh"

print(archive_handler({
    "docid":"199",
    "userid":"36",
    "archivedate":"2.2.2020."
}, ""))
