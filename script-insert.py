
import pandas as pd
import pyodbc

# Import CSV
data = pd.read_csv (r'C:\Users\Ron\Desktop\Test\products.csv') 
df = pd.DataFrame(data)

# Connect to SQL Server
conn = pyodbc.connect('Driver={SQL Server};'
'Server=RON\SQLEXPRESS;'
'Database=decidim_equipo1.db;'
'Trusted_Connection=yes;')
cursor = conn.cursor()


# Insert DataFrame to Table

for row in df.itertuples():
cursor.execute('''
INSERT INTO authors (id, nickname, password)
VALUES (?,?,?)
''',
 row.id,
 row.nickname,
 ''
)
conn.commit()


for row in df.itertuples():
cursor.execute('''
INSERT INTO proposal (id, id_authors, title, id_authors, id_comments, n_endorsement, created_at, published_at)
VALUES (?,?,?,?,?,?,?,?)
''',
row.id,
 row.id_authors,
 row.title,
 row.id_authors,
 row.id_comments,
 row.n_endorsement,
 row.created_at,
 row.published_at
)
conn.commit()


for row in df.itertuples():
cursor.execute('''
INSERT INTO comments (id, id_authors, body, id_comments, created_at, down_votes, up_votes, reply_to, reply_level)
VALUES (?,?,?,?,?,?,?,?,?)
''',
row.id,
 row.id_authors,
 row.body,
 row.id_comments,
 row.created_at,
 row.down_votes,
 row.up_votes,
 row.reply_to,
 row.reply_level
)
conn.commit()
