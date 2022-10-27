class Document():
    def __init__(self, *args):
        if len(args)==1:
            self.text=args[0][0]
            self.type=args[0][1]
            self.label=args[0][2]
            self.docid=args[0][3]
            self.uspjeh=args[0][4]
        else:
            self.text=""
            self.label=""
            self.type=""
            self.docid=""
            self.uspjeh=""

    def addDocument(self, userid, scandate, cursor,connection):
        cursor.execute("select * from users where userid='{0}'"
        .format(userid))

        userrole=cursor.fetchone()[5]

        cursor.execute("insert into scanHistory(userid, scanDate) values ('{0}','{1}') returning docid"
        .format(userid,scandate))
        self.docid=cursor.fetchone()[0]
        connection.commit()

        if self.label=="":
            cursor.execute("insert into documents(docid, doclabel, doctype, doctext) values ('{0}',null,null,'{1}')"
            .format(self.docid,self.text))
            connection.commit()
            return {
                "message":"Neispravan scan poslan kao ispravan"
            }

        cursor.execute("select * from documents where doclabel='{0}'"
        .format(self.label))

        isti=cursor.fetchall()

        if len(isti)!=0:
            return {}

        cursor.execute("insert into documents(docid, doclabel, doctype, doctext) values ('{0}','{1}','{2}','{3}')"
        .format(self.docid,self.label,self.type,self.text))
        connection.commit()

        if self.uspjeh=="da":
            if userrole=='revizor':
                cursor.execute("select * from accountantresponsibility where responsibility='{0}'"
                .format(self.type))

                accountantID=cursor.fetchone()[0]

                cursor.execute("insert into accountantpending(docid, userid, signaturepending) values ('{0}','{1}','{2}')"
                .format(self.docid,accountantID,"0"))
                connection.commit()
                return{
                    "message":"dodan revizorov dokument"
                }
            else:
                cursor.execute("select * from users where userrole='{0}'"
                .format("revizor"))

                reviserID=cursor.fetchone()[0]

                cursor.execute("insert into reviserpending(docid, userid) values ('{0}','{1}')"
                .format(self.docid,reviserID))
                connection.commit()
                return {
                    "message":"dodan dokument"
                }


            




