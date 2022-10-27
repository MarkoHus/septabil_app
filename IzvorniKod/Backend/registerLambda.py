import psycopg2
from user import User



def register_handler(event, context):

    connection=psycopg2.connect(
        database="IS2_Digitalizacija",
        user="postgres",
        password="bazepodataka",
        host="is2digitalizacijav2.cwck16atirhq.eu-central-1.rds.amazonaws.com"
    )

    cur=connection.cursor()
    
    name=event["name"]
    surname=event["surname"]
    username=event["username"]
    password=event["password"]
    role=event["role"]

    cur.execute("select * from users where username='{0}'".format(username))

    sameUsername=cur.fetchall()

    if len(sameUsername)==0:

        user=User([name,surname,username,password,role])
        user.register(cur, connection)
        message="SignupValid"
    
    else:
        message="SignupInvalid"

    return {
        "message":message
    }


        


