import psycopg2
import psycopg2.extras
import json



def signed_handler(event, context):

    connection=psycopg2.connect(
        database="IS2_Digitalizacija",
        user="postgres",
        password="bazepodataka",
        host="is2digitalizacijav2.cwck16atirhq.eu-central-1.rds.amazonaws.com"
    )

    cur=connection.cursor(cursor_factory = psycopg2.extras.RealDictCursor)

    docID=event["docid"]

    cur.execute("select userId from accountantpending where docId='{0}'"
    .format(docID))

    userID=(cur.fetchone())["userid"]

    cur.execute("update accountantpending set signaturepending=2 where docid='{0}'"
    .format(docID))
    connection.commit()

    cur.execute("delete from executivepending where docid='{0}'"
    .format(docID))
    connection.commit()
    
    return "uspjeh"

