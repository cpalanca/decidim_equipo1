from fastapi import FastAPI
import sqlite3
import datetime
from pydantic import BaseModel

app = FastAPI()

class AuthorsParams(BaseModel):
    id: int
    nickname: str

@app.get("/authors")
async def select_all():
    connection = sqlite3.connect('/mnt/hgfs/OneDrive - Deutsche Telekom AG/Proyecto_tugr/BBDD/decidim_equipo1.db')
    cursor = connection.cursor()

    cursor.execute("SELECT * from authors")
    records = cursor.fetchall()

    cursor.close()
    connection.commit()
    return records

@app.get("/authors/{param1}")
async def select_with_condition(param1: int):
    connection = sqlite3.connect('/mnt/hgfs/OneDrive - Deutsche Telekom AG/Proyecto_tugr/BBDD/decidim_equipo1.db')
    cursor = connection.cursor()

    cursor.execute("SELECT * FROM authors WHERE id=?", (str(param1)))
    records = cursor.fetchall()

    cursor.close()
    connection.commit()
    return records

@app.delete("/authors/{param1}")
async def delete_with_condition(param1: int):
    connection = sqlite3.connect('/mnt/hgfs/OneDrive - Deutsche Telekom AG/Proyecto_tugr/BBDD/decidim_equipo1.db')
    cursor = connection.cursor()

    cursor.execute("DELETE FROM authors WHERE id=?", (str(param1)))
    records = cursor.fetchall()

    cursor.close()
    connection.commit()
    return records

@app.post("/authors")
async def insert(params: AuthorsParams):
    connection = sqlite3.connect('/mnt/hgfs/OneDrive - Deutsche Telekom AG/Proyecto_tugr/BBDD/decidim_equipo1.db')
    cursor = connection.cursor()

    new_record = (params.id, params.nickname)
    cursor.execute("INSERT INTO authors(id, nickname) VALUES(?,?)", (new_record))
    records = cursor.fetchall()

    cursor.close()
    connection.commit()
    return records

@app.put("/authors/{param1}")
async def update(param1: int, params: AuthorsParams):
    connection = sqlite3.connect('/mnt/hgfs/OneDrive - Deutsche Telekom AG/Proyecto_tugr/BBDD/decidim_equipo1.db')
    cursor = connection.cursor()

    cursor.execute("UPDATE authors SET nickname = ? WHERE id = ?", (params.nickname, param1))
    records = cursor.fetchall()

    cursor.close()
    connection.commit()
    return records

class CommentsParams(BaseModel):
    id: int
    id_authors: int
    body: str
    id_comments: int
    created_at: datetime.date
    down_votes: int
    up_votes: int
    reply_to: int
    reply_level: int

@app.get("/comments")
async def select_all():
    connection = sqlite3.connect('/mnt/hgfs/OneDrive - Deutsche Telekom AG/Proyecto_tugr/BBDD/decidim_equipo1.db')
    cursor = connection.cursor()

    cursor.execute("SELECT * from comments")
    records = cursor.fetchall()

    cursor.close()
    connection.commit()
    return records

@app.get("/comments/{param1}")
async def select_with_condition(param1: int):
    connection = sqlite3.connect('/mnt/hgfs/OneDrive - Deutsche Telekom AG/Proyecto_tugr/BBDD/decidim_equipo1.db')
    cursor = connection.cursor()

    cursor.execute("SELECT * FROM comments WHERE id=?", (str(param1)))
    records = cursor.fetchall()

    cursor.close()
    connection.commit()
    return records

@app.delete("/comments/{param1}")
async def delete_with_condition(param1: int):
    connection = sqlite3.connect('/mnt/hgfs/OneDrive - Deutsche Telekom AG/Proyecto_tugr/BBDD/decidim_equipo1.db')
    cursor = connection.cursor()

    cursor.execute("DELETE FROM comments WHERE id=?", (str(param1)))
    records = cursor.fetchall()

    cursor.close()
    connection.commit()
    return records

@app.post("/comments")
async def insert(params: CommentsParams):
    connection = sqlite3.connect('/mnt/hgfs/OneDrive - Deutsche Telekom AG/Proyecto_tugr/BBDD/decidim_equipo1.db')
    cursor = connection.cursor()

    new_record = (params.id, params.id_authors, params.body, params.id_comments, params.created_at, params.down_votes, params.up_votes, params.reply_to, params.reply_level)
    cursor.execute("INSERT INTO comments(id, id_authors, body, id_comments, created_at, down_votes, up_votes, reply_to, reply_level) VALUES(?,?,?,?,?,?,?,?,?)", (new_record))
    records = cursor.fetchall()

    cursor.close()
    connection.commit()
    return records

@app.put("/comments/{param1}")
async def update(param1: int, params: CommentsParams):
    connection = sqlite3.connect('/mnt/hgfs/OneDrive - Deutsche Telekom AG/Proyecto_tugr/BBDD/decidim_equipo1.db')
    cursor = connection.cursor()

    cursor.execute("UPDATE comments SET id_authors = ?, body = ?, id_comments = ?, created_at = ?, down_votes = ?, up_votes = ?, reply_to = ?, reply_level = ? WHERE id = ?", (params.id_authors, params.body, params.id_comments, params.created_at, params.down_votes, params.up_votes, params.reply_to, params.reply_level, param1))
    records = cursor.fetchall()

    cursor.close()
    connection.commit()
    return records

class proposalParams(BaseModel):
    id: int
    title: str
    id_authors: int
    id_comments: str
    n_endorsement: int
    created_at: datetime.date
    published_at: datetime.date

@app.get("/proposal")
async def select_all():
    connection = sqlite3.connect('/mnt/hgfs/OneDrive - Deutsche Telekom AG/Proyecto_tugr/BBDD/decidim_equipo1.db')
    cursor = connection.cursor()

    cursor.execute("SELECT * from proposal")
    records = cursor.fetchall()

    cursor.close()
    connection.commit()
    return records

@app.get("/proposal/{param1}")
async def select_with_condition(param1: int):
    connection = sqlite3.connect('/mnt/hgfs/OneDrive - Deutsche Telekom AG/Proyecto_tugr/BBDD/decidim_equipo1.db')
    cursor = connection.cursor()

    cursor.execute("SELECT * FROM proposal WHERE id=?", (str(param1)))
    records = cursor.fetchall()

    cursor.close()
    connection.commit()
    return records

@app.delete("/proposal/{param1}")
async def delete_with_condition(param1: int):
    connection = sqlite3.connect('/mnt/hgfs/OneDrive - Deutsche Telekom AG/Proyecto_tugr/BBDD/decidim_equipo1.db')
    cursor = connection.cursor()

    cursor.execute("DELETE FROM proposal WHERE id=?", (str(param1)))
    records = cursor.fetchall()

    cursor.close()
    connection.commit()
    return records

@app.post("/proposal")
async def insert(params: proposalParams):
    connection = sqlite3.connect('/mnt/hgfs/OneDrive - Deutsche Telekom AG/Proyecto_tugr/BBDD/decidim_equipo1.db')
    cursor = connection.cursor()

    new_record = (params.id, params.title, params.id_authors, params.id_comments, params.n_endorsement, params.created_at, params.published_at)
    cursor.execute("INSERT INTO comments(id, title, id_authors, id_comments, endorsement, created_at, published_at) VALUES(?,?,?,?,?,?,?)", (new_record))
    records = cursor.fetchall()

    cursor.close()
    connection.commit()
    return records

@app.put("/proposal/{param1}")
async def update(param1: int, params: proposalParams):
    connection = sqlite3.connect('/mnt/hgfs/OneDrive - Deutsche Telekom AG/Proyecto_tugr/BBDD/decidim_equipo1.db')
    cursor = connection.cursor()

    cursor.execute("UPDATE proposal SET title = ?, id_authors = ?, id_comments = ?, endorsement = ?, created_at = ?, published_at = ? WHERE id = ?", (params.title, params.id_authors, params.id_comments, params.n_endorsement, params.created_at, params.published_at, param1))
    records = cursor.fetchall()

    cursor.close()
    connection.commit()
    return records