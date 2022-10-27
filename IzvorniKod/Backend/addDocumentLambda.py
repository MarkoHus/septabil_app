import psycopg2
from document import Document



def add_document_handler(event, context):

    connection=psycopg2.connect(
        database="IS2_Digitalizacija",
        user="postgres",
        password="bazepodataka",
        host="is2digitalizacijav2.cwck16atirhq.eu-central-1.rds.amazonaws.com"
    )

    cur=connection.cursor()

    document=Document([event["doctext"],
                      event["doctype"],
                      event["doclabel"],
                      "",
                      event["uspjeh"]])
    
    document.addDocument(event["userid"],event["scandate"], cur, connection)
