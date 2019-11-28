package pl.schoolManagement.util;

public class DBDAO {

    public static short createTables(){
        short tablesCreated = 0;
        String stmt;
        if(!DBUtil.tableExists("users")){
            try{
                stmt = "CREATE TABLE IF NOT EXISTS users (\n" +
                        "    user_id INTEGER PRIMARY KEY AUTOINCREMENT\n" +
                        "                    NOT NULL,\n" +
                        "    login   TEXT    NOT NULL,\n" +
                        "    pass    TEXT    NOT NULL,\n" +
                        "    rank    INTEGER NOT NULL\n" +
                        "                    DEFAULT (0),\n" +
                        "    name    TEXT    NOT NULL,\n" +
                        "    surname TEXT    NOT NULL,\n" +
                        "    email   TEXT    NOT NULL,\n" +
                        "    phone   INTEGER NOT NULL\n" +
                        "                    DEFAULT (0)\n" +
                        ");";
                DBUtil.dbExecuteUpdate(stmt);
                tablesCreated++;
                addAdmin();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(!DBUtil.tableExists("subjects")){
            try{
                stmt = "CREATE TABLE IF NOT EXISTS subjects (\n" +
                        "    subject_id  INTEGER PRIMARY KEY AUTOINCREMENT\n" +
                        "                        NOT NULL,\n" +
                        "    name        TEXT    NOT NULL,\n" +
                        "    description TEXT\n" +
                        ");";
                DBUtil.dbExecuteUpdate(stmt);
                tablesCreated++;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(!DBUtil.tableExists("grades")){
            try{
                stmt = "CREATE TABLE IF NOT EXISTS grades (\n" +
                        "    grade_id    INTEGER PRIMARY KEY AUTOINCREMENT\n" +
                        "                        NOT NULL,\n" +
                        "    value       INTEGER NOT NULL\n" +
                        "                        CHECK (value >= 1 AND\n" +
                        "                               value <= 6),\n" +
                        "    multiple    INTEGER DEFAULT (1)\n" +
                        "                        NOT NULL,\n" +
                        "    student_id  INTEGER REFERENCES users (user_id) ON DELETE CASCADE\n" +
                        "                        NOT NULL,\n" +
                        "    teacher_id  INTEGER REFERENCES users (user_id) ON DELETE CASCADE\n" +
                        "                        NOT NULL,\n" +
                        "    subject_id  INTEGER REFERENCES subjects (subject_id) ON DELETE CASCADE\n" +
                        "                        NOT NULL,\n" +
                        "    date        TEXT    NOT NULL,\n" +
                        "    description TEXT\n" +
                        ");";
                DBUtil.dbExecuteUpdate(stmt);
                tablesCreated++;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(!DBUtil.tableExists("whoseSubject")){
            try{
                stmt = "CREATE TABLE IF NOT EXISTS whoseSubject (\n" +
                        "    user_id    INTEGER REFERENCES users (user_id) ON DELETE CASCADE\n" +
                        "                       NOT NULL,\n" +
                        "    subject_id INTEGER REFERENCES subjects (subject_id) ON DELETE CASCADE\n" +
                        "                       NOT NULL\n" +
                        ");\n";
                DBUtil.dbExecuteUpdate(stmt);
                tablesCreated++;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return tablesCreated;
    }

    private static void addAdmin(){
        try{
            String stmt = "INSERT INTO users (login, pass, rank, name, surname, email) VALUES (\"admin\", \"admin\", 100, \"admin\", \"admin\", \"admin@sms.edu\")";
            DBUtil.dbExecuteUpdate(stmt);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void populateTables(){
        try{
            String stmt = "INSERT INTO users (login, pass, rank, name, surname, email, phone) VALUES\n" +
                    "('grzegorzzajac', 'Szarak1234', '1', 'Grzegorz', 'Zając', 'g.zajac@gmail.com', '567390253'),\n" +
                    "('stanislawostrowski', 'rocket987', '1', 'Stanisław', 'Ostrowski', 'stanislaw.ostrowski@gmail.com', '546039245'),\n" +
                    "('barbarajablonska', 'Pitagoras2', '1', 'Barbara', 'Jabłońska', 'b.jablonska@gmail.com', '500025690'),\n" +
                    "('andrzejduda', 'ceezoo1ieL', '1', 'Andrzej', 'Duda', 'andrzej.duda@gmail.com', '744098116'),\n" +
                    "('edytaczerwinska', 'Hanna9', '1', 'Edyta', 'Czerwińska', 'e.czerwinska@yahoo.com', '555032941'),\n" +
                    "('elzbietakucharska', 'ElaEla111', '1', 'Elżbieta', 'Kucharska', 'elzbieta_kucharska@gmail.com', '699301400'),\n" +
                    "('alinamajewska', 'majKa1994', '0', 'Alina', 'Majewska', 'alinam94@gmail.com', '730222745'),\n" +
                    "('blazejadamczyk', 'zaq1@WSX', '0', 'Błażej', 'Adamczyk', 'adamczyk.blazej@gmail.com', '0'),\n" +
                    "('karolinawisniewska', 'Sesee1994', '0', 'Karolina', 'Wiśniewska', 'karo.wisnia@op.pl', '664320456'),\n" +
                    "('patrykwieczorek', 'TSW1906', '0', 'Patryk', 'Wieczorek', 'patryk1906TS@gmail.com', '504672300'),\n" +
                    "('patrycjapawlowska', 'Virgo4beith', '0', 'Patrycja', 'Pawłowska', 'p.pawlowska@gmail.com', '534095233'),\n" +
                    "('zosiawalczak', 'hilte1234', '0', 'Zosia', 'Walczyk', 'zosiawalczyk@gmail.com', '503222560'),\n" +
                    "('pawelkucharski', 'asd!@#zxc', '0', 'Paweł', 'Kucharski', 'pawelkucharski@gmail.com', '0'),\n" +
                    "('dominiknowakowski', 'Krakow1995', '0', 'Dominik', 'Nowakowski', 'domnowa@gmail.com', '605337823'),\n" +
                    "('annakowal', 'gemininim', '0', 'Anna', 'Kowal', 'anka_kowal@yahoo.com', '544033891'),\n" +
                    "('weronikakozlowska', 'LeoMessi13', '0', 'Weronika', 'Kozłowska', 'weronikakozlowska@gmail.com', '0'),\n" +
                    "('mateuszjasinski', 'DeiwCo3', '0', 'Mateusz', 'Jasiński', 'jasinski.m@gmail.com','0'),\n" +
                    "('szymonkrol', 'BezpieczneHaslo1234', '0', 'Szymon', 'Król', 'krol.szymon@gmail.com', '690247811'),\n" +
                    "('emilwojciechowski', 'Skala90', '0', 'Emil', 'Wojciechowski', 'emilo44@wp.pl', '520203041'),\n" +
                    "('damiannowicki', 'nowy0123', '0', 'Damian', 'Nowicki', 'damiannn@gmail.com', '689300182'),\n" +
                    "('jannowak', 'PokerS', '0', 'Jan', 'Nowak', 'jan.nowak354@gmail.com', '0');\n" +
                    "\n" +
                    "INSERT INTO subjects (name, description) VALUES\n" +
                    "('Matematyka dyskretna', 'Zbiorcza nazwa wszystkich działów matematyki, które zajmują się badaniem struktur nieciągłych, to znaczy zawierających zbiory co najwyżej przeliczalne, czyli dyskretne'),\n" +
                    "('Analiza matematyczna i algebra liniowa', 'Analiza matematyczna i algebra liniowa'),\n" +
                    "('Systemy operacyjne' , 'Windows, Linux, Mac OS, wirtualne systemy'),\n" +
                    "('Bazy danych' , 'Tworzenia i zarządzanie bazami danych'),\n" +
                    "('Język angielski', 'Poziom B2'),\n" +
                    "('Podstawy programowania' , '');\n" +
                    "\n" +
                    "INSERT INTO whoseSubject (user_id, subject_id) VALUES\n" +
                    "('1', '3'),\n" +
                    "('1', '6'),\n" +
                    "('2', '4'),\n" +
                    "('3', '1'),\n" +
                    "('3', '2'),\n" +
                    "('6', '2'),\n" +
                    "('5', '5'),\n" +
                    "('4', '1'),\n" +
                    "('7', '5'),\n" +
                    "('7', '6'),\n" +
                    "('8', '1'),\n" +
                    "('8', '2'),\n" +
                    "('8', '6'),\n" +
                    "('9', '1'),\n" +
                    "('9', '5'),\n" +
                    "('10', '4'),\n" +
                    "('10', '3'),\n" +
                    "('11', '5'),\n" +
                    "('11', '3'),\n" +
                    "('11', '4'),\n" +
                    "('12', '6'),\n" +
                    "('12', '2'),\n" +
                    "('13', '1'),\n" +
                    "('13', '6'),\n" +
                    "('13', '4'),\n" +
                    "('14', '2'),\n" +
                    "('14', '5'),\n" +
                    "('15', '2'),\n" +
                    "('15', '1'),\n" +
                    "('16', '5'),\n" +
                    "('17', '3'),\n" +
                    "('18', '6'),\n" +
                    "('18', '1'),\n" +
                    "('18', '4'),\n" +
                    "('19', '3'),\n" +
                    "('19', '1'),\n" +
                    "('20', '1'),\n" +
                    "('21', '1'),\n" +
                    "('21', '6'),\n" +
                    "('21', '3');\n" +
                    "\n" +
                    "INSERT INTO grades (value, multiple, student_id, teacher_id, subject_id, date, description) VALUES\n" +
                    "('4', '3', '7', '5', '5', '14.10.2018', 'sprawdzian U1'),\n" +
                    "('3', '3', '9', '5', '5', '14.10.2018', 'sprawdzian U1'),\n" +
                    "('5', '3', '11', '5', '5', '14.10.2018', 'sprawdzian U1'),\n" +
                    "('2', '3', '14', '5', '5', '14.10.2018', 'sprawdzian U1'),\n" +
                    "('3', '3', '16', '5', '5', '14.10.2018', 'sprawdzian U1'),\n" +
                    "('4', '1', '7', '5', '5', '07.10.2018', 'kartkówka'),\n" +
                    "('4', '1', '9', '5', '5', '07.10.2018', 'kartkówka'),\n" +
                    "('5', '1', '11', '5', '5', '07.10.2018', 'kartkówka'),\n" +
                    "('3', '1', '14', '5', '5', '07.10.2018', 'kartkówka'),\n" +
                    "('2', '1', '16', '5', '5', '07.10.2018', 'kartkówka'),\n" +
                    "('3', '3', '8', '3', '1', '13.10.2018', 'kolokwium'),\n" +
                    "('2', '3', '9', '3', '1', '13.10.2018', 'kolokwium'),\n" +
                    "('2', '3', '13', '3', '1', '13.10.2018', 'kolokwium'),\n" +
                    "('3', '3', '15', '3', '1', '13.10.2018', 'kolokwium'),\n" +
                    "('4', '3', '18', '3', '1', '13.10.2018', 'kolokwium'),\n" +
                    "('3', '3', '19', '3', '1', '13.10.2018', 'kolokwium'),\n" +
                    "('2', '3', '20', '3', '1', '13.10.2018', 'kolokwium'),\n" +
                    "('3', '3', '21', '3', '1', '13.10.2018', 'kolokwium'),\n" +
                    "('3', '2', '8', '3', '1', '06.10.2018', 'małe kolokwium'),\n" +
                    "('2', '2', '9', '3', '1', '06.10.2018', 'małe kolokwium'),\n" +
                    "('2', '2', '13', '3', '1', '06.10.2018', 'małe kolokwium'),\n" +
                    "('3', '2', '15', '3', '1', '06.10.2018', 'małe kolokwium'),\n" +
                    "('4', '2', '18', '3', '1', '06.10.2018', 'małe kolokwium'),\n" +
                    "('3', '2', '19', '3', '1', '06.10.2018', 'małe kolokwium'),\n" +
                    "('2', '2', '20', '3', '1', '06.10.2018', 'małe kolokwium'),\n" +
                    "('3', '2', '21', '3', '1', '06.10.2018', 'małe kolokwium'),\n" +
                    "('4', '1', '8', '4', '1', '07.10.2018', 'test'),\n" +
                    "('3', '1', '9', '4', '1', '07.10.2018', 'test'),\n" +
                    "('3', '1', '13', '4', '1', '07.10.2018', 'test'),\n" +
                    "('4', '1', '15', '4', '1', '07.10.2018', 'test'),\n" +
                    "('5', '1', '18', '4', '1', '07.10.2018', 'test'),\n" +
                    "('3', '1', '19', '4', '1', '07.10.2018', 'test'),\n" +
                    "('2', '1', '20', '4', '1', '07.10.2018', 'test'),\n" +
                    "('3', '1', '21', '4', '1', '07.10.2018', 'test'),\n" +
                    "('3', '2', '8', '3', '2', '07.10.2018', 'małe kolokwium'),\n" +
                    "('2', '2', '12', '3', '2', '07.10.2018', 'małe kolokwium'),\n" +
                    "('2', '2', '14', '3', '2', '07.10.2018', 'małe kolokwium'),\n" +
                    "('3', '2', '15', '3', '2', '07.10.2018', 'małe kolokwium'),\n" +
                    "('3', '2', '8', '3', '2', '07.10.2018', 'małe kolokwium'),\n" +
                    "('4', '1', '10', '1', '3', '14.10.2018', 'sprawozdanie'),\n" +
                    "('3', '1', '11', '1', '3', '14.10.2018', 'sprawozdanie'),\n" +
                    "('4', '1', '17', '1', '3', '14.10.2018', 'sprawozdanie'),\n" +
                    "('5', '1', '19', '1', '3', '14.10.2018', 'sprawozdanie'),\n" +
                    "('3', '1', '21', '1', '3', '14.10.2018', 'sprawozdanie'),\n" +
                    "('4', '3', '10' , '2', '4', '14.10.2018', 'baza danych'),\n" +
                    "('2', '3', '11' , '2', '4', '14.10.2018', 'baza danych'),\n" +
                    "('3', '3', '13' , '2', '4', '14.10.2018', 'baza danych'),\n" +
                    "('5', '3', '18' , '2', '4', '14.10.2018', 'baza danych'),\n" +
                    "('5', '1', '10' , '2', '4', '07.10.2018', 'praca na lekcji'),\n" +
                    "('3', '1', '11' , '2', '4', '07.10.2018', 'praca na lekcji'),\n" +
                    "('2', '1', '13' , '2', '4', '07.10.2018', 'praca na lekcji'),\n" +
                    "('5', '1', '18' , '2', '4', '07.10.2018', 'praca na lekcji'),\n" +
                    "('2', '3', '7', '1', '6', '13.10.2018', 'program'),\n" +
                    "('3', '3', '8', '1', '6', '13.10.2018', 'program'),\n" +
                    "('2', '3', '12', '1', '6', '13.10.2018', 'program'),\n" +
                    "('3', '3', '13', '1', '6', '13.10.2018', 'program'),\n" +
                    "('4', '3', '18', '1', '6', '13.10.2018', 'program'),\n" +
                    "('3', '3', '21', '1', '6', '13.10.2018', 'program'),\n" +
                    "('2', '2', '7', '1', '6', '06.10.2018', 'test'),\n" +
                    "('3', '2', '8', '1', '6', '06.10.2018', 'test'),\n" +
                    "('3', '2', '12', '1', '6', '06.10.2018', 'test'),\n" +
                    "('4', '2', '13', '1', '6', '06.10.2018', 'test'),\n" +
                    "('4', '2', '18', '1', '6', '06.10.2018', 'test'),\n" +
                    "('3', '2', '21', '1', '6', '06.10.2018', 'test');";
            DBUtil.dbExecuteUpdate(stmt);
            System.out.println("Populated tables with demo data");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
