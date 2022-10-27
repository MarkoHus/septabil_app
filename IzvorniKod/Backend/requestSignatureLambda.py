import psycopg2
import psycopg2.extras
import json



def request_signature_handler(event, context):

    connection=psycopg2.connect(
        database="IS2_Digitalizacija",
        user="postgres",
        password="bazepodataka",
        host="is2digitalizacijav2.cwck16atirhq.eu-central-1.rds.amazonaws.com"
    )

    cur=connection.cursor(cursor_factory = psycopg2.extras.RealDictCursor)

    userID=event["userid"]
    docID=event["docid"]

    cur.execute("insert into executivepending(userid, docid) values ('{0}','{1}')"
    .format(userID, docID))
    connection.commit()

    cur.execute("update accountantpending set signaturepending=1 where docid='{0}'"
    .format(docID))
    connection.commit()

    return "uspjeh"

