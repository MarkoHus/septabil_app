import psycopg2
import psycopg2.extras
import json



def see_pending_handler(event, context):

    connection=psycopg2.connect(
        database="IS2_Digitalizacija",
        user="postgres",
        password="bazepodataka",
        host="is2digitalizacijav2.cwck16atirhq.eu-central-1.rds.amazonaws.com"
    )

    cur=connection.cursor(cursor_factory = psycopg2.extras.RealDictCursor)

    userID=event["userid"]

    cur.execute("select * from users where userid='{0}'"
    .format(userID))

    userRole=cur.fetchone()["userrole"]

    response="{"

    if userRole=="revizor":
        cur.execute("select * from reviserpending natural join documents  where userid='{0}'"
        .format(userID))
    
    elif userRole=="racunovoda":
        cur.execute("select * from accountantpending natural join documents  where userid='{0}'"
        .format(userID))

        dokumenti=cur.fetchall()

        length=str(len(dokumenti))
        response+="'length': '"+length+"',"

        if len(dokumenti)>0:

            for i in range(len(dokumenti)):
                dokument="'id"""+str(i+1)+"': '"+str((dokumenti[i])["docid"])+"',"+"'scan"+str(i+1)+"': '''"+((dokumenti[i])["doctext"]).strip('\n')+"''',"+"'signed"+str(i+1)+"': '"+str((dokumenti[i])["signaturepending"])+"',"
                response=response+dokument

            response=response[:-1]

            response+="}"
        else:
            return {
                "length":length
            }

        return eval(response)


    elif userRole=="direktor":
        cur.execute("select * from executivepending natural join documents")

    dokumenti=cur.fetchall()

    length=str(len(dokumenti))
    response+="'length': '"+length+"',"

    if len(dokumenti)>0:

        for i in range(len(dokumenti)):
            dokument="'id"""+str(i+1)+"': '"+str((dokumenti[i])["docid"])+"',"+"'scan"+str(i+1)+"': '''"+((dokumenti[i])["doctext"]).strip('\n')+"''',"
            response=response+dokument

        response=response[:-1]

        response+="}"
    else:
        return {
            "length":length
        }

    return eval(response)









