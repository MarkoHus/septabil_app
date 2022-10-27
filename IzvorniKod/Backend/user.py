class User:
    def __init__(self, *args):
        if len(args)==1:
            self.name=args[0][0]
            self.surname=args[0][1]
            self.username=args[0][2]
            self.password=args[0][3]
            self.role=args[0][4]
        else:
            self.name=""
            self.surname=""
            self.username=""
            self.password=""
            self.role=""
    
    def register(self, cursor,connection):
        cursor.execute("insert into users(firstname,lastname,username,userpswd,userrole) values ('{0}','{1}','{2}','{3}','{4}')"
        .format(self.name,self.surname,self.username,self.password,self.role))
        connection.commit()