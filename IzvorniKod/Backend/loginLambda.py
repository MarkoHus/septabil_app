import psycopg2
from user import User


def login_response(user, message, id):
    return{
        "message":message,
        "username":user.username,
        "ime":user.name,
        "prezime":user.surname,
        "role":user.role,
        "id":id
    }

def login_handler(event, context):

    connection=psycopg2.connect(
        database="IS2_Digitalizacija",
        user="postgres",
        password="bazepodataka",
        host="is2digitalizacijav2.cwck16atirhq.eu-central-1.rds.amazonaws.com"
    )

    cur=connection.cursor()


    username=event["username"]
    #Upit u kojem se provjerava postoji li korisnik s ovim korisnickim imenom
    cur.execute("select * from users where username='{0}'".format(username) )

    users=cur.fetchall()

    #Postoji li user
    if len(users)!=0:
        user=User(users[0][1::])
        userid=users[0][0]
        #Podudara li se password
        if user.password==event["password"]:
            message="LoginValid"
        else:
            message="LoginInvalid"
    else:
        user=User()
        message="LoginInvalid"
        userid=""

    return login_response(user, message, userid)




