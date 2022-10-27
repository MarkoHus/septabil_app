import psycopg2
import psycopg2.extras
import json


def reviser_check_handler(event, context):

    connection=psycopg2.connect(
        database="IS2_Digitalizacija",
        user="postgres",
        password="bazepodataka",
        host="is2digitalizacijav2.cwck16atirhq.eu-central-1.rds.amazonaws.com"
    )

    cur=connection.cursor(cursor_factory = psycopg2.extras.RealDictCursor)
    
    userId=event["userid"]
    docID=event["docid"]

    cur.execute("select * from reviserpending natural join documents  where docid='{0}'"
    .format(docID))

    dokument=cur.fetchone()
    dokumentID=dokument["docid"]

    cur.execute("select userid from accountantresponsibility  where responsibility='{0}'"
    .format(dokument["doctype"]))

    accountantID=(cur.fetchone())["userid"]

    cur.execute("insert into accountantpending(docid, userid, signaturepending) values ('{0}','{1}', 0)"
    .format(dokumentID,accountantID))
    connection.commit()

    cur.execute("delete from reviserpending where docid='{0}'"
    .format(dokumentID))
    connection.commit()
    
    return accountantID

