from fastapi import FastAPI, HTTPException
from datetime import datetime
from typing import Optional
import sqlite3
from fastapi.encoders import jsonable_encoder
from fastapi.responses import JSONResponse
from pydantic import BaseModel

app = FastAPI()

class Author(BaseModel):
    id: int
    nickname: str

@app.get("/authors")
async def select_all():
    connection = sqlite3.connect('/home/tsi/equipo1/decidim_equipo1.db')
    cursor = connection.cursor()

    cursor.execute("SELECT * from authors")
    records = cursor.fetchall()
    authors = []
    columnNames = [column[0] for column in cursor.description]

    for record in records:
        authors.append( dict( zip( columnNames , record ) ) )
    json_compatible_item_data = jsonable_encoder(authors)

    return JSONResponse(content=json_compatible_item_data)


@app.get("/authors/{id}")
async def select_with_condition(id: int):
    connection = sqlite3.connect('/home/tsi/equipo1/decidim_equipo1.db')
    cursor = connection.cursor()
    try:
        cursor.execute("SELECT * FROM authors WHERE id=?", [str(id)])
        records = cursor.fetchall()
        cursor.close()
        columnNames = [column[0] for column in cursor.description]

        for record in records:
            json_compatible_item_data = jsonable_encoder(dict( zip( columnNames , record ) ) )
            
        return JSONResponse(content=json_compatible_item_data)
    except Exception:
        return HTTPException(status_code=404, detail="Item not found")

@app.delete("/authors/{id}")
async def delete_with_condition(id: int):
    connection = sqlite3.connect('/home/tsi/equipo1/decidim_equipo1.db')
    cursor = connection.cursor()
    cursor.execute("SELECT * FROM authors WHERE id=?", [str(id)])
    rs = cursor.fetchall()
    
    if len(rs) > 0:
        cursor.execute("DELETE FROM authors WHERE id=?", [str(id)])
        cursor.close()
        connection.commit()
        return {"message": "El Author ha sido eliminado correctamente."}
    raise HTTPException(status_code=404, detail="Author no encontrado.")

@app.post("/authors")
async def insert(item: Author):
    connection = sqlite3.connect('/home/tsi/equipo1/decidim_equipo1.db')
    cursor = connection.cursor()

    new_record = (item.id, item.nickname)
    try:
        cursor.execute("INSERT INTO authors(id, nickname) VALUES(?,?)", (new_record))
        records = cursor.fetchall()
        cursor.close()
        connection.commit()
        return {"message": "El usuario a sido creado correctamente"}
    except:
        return HTTPException(status_code=404, detail="El usuario ya existe, no puedes insertar un author con el mismo ID")


@app.put("/authors/{id_author}")
async def update(id_author: int, item: Author):
    try:
        connection = sqlite3.connect('/home/tsi/equipo1/decidim_equipo1.db')
        cursor = connection.cursor()
        cursor.execute("UPDATE authors SET nickname = ? WHERE id = ?", (item.nickname, id_author))
        cursor.close()
        connection.commit()
        json_compatible_item_data = jsonable_encoder(item)
        return JSONResponse(content=json_compatible_item_data)
    except:
        return HTTPException(status_code=404, detail="Item not found")

class Comments(BaseModel):
    id: int
    id_authors: int
    body: str
    id_comments: str
    created_at: datetime
    down_votes: int
    up_votes: int
    reply_to: str
    reply_level: int

@app.get("/comments")
async def select_all():  
    connection = sqlite3.connect('/home/tsi/equipo1/decidim_equipo1.db')
    cursor = connection.cursor()

    cursor.execute("SELECT * from comments")
    records = cursor.fetchall()
    comments = []
    columnNames = [column[0] for column in cursor.description]

    for record in records:
        comments.append( dict( zip( columnNames , record ) ) )
    json_compatible_item_data = jsonable_encoder(comments)
        
    return JSONResponse(content=json_compatible_item_data)
        

@app.get("/comments/{id}")
async def select_with_condition(id: int):
    connection = sqlite3.connect('/home/tsi/equipo1/decidim_equipo1.db')
    cursor = connection.cursor()
    try:
        cursor.execute("SELECT * FROM comments WHERE id=?", [str(id)])
        records = cursor.fetchall()
        cursor.close()
        columnNames = [column[0] for column in cursor.description]

        for record in records:
            json_compatible_item_data = jsonable_encoder(dict( zip( columnNames , record ) ) )
            
        return JSONResponse(content=json_compatible_item_data)
    except Exception:
        return HTTPException(status_code=404, detail="Item not found")

@app.delete("/comments/{id}")
async def delete_with_condition(id: int):
    connection = sqlite3.connect('/home/tsi/equipo1/decidim_equipo1.db')
    cursor = connection.cursor()
    cursor.execute("SELECT * FROM comments WHERE id=?", [str(id)])
    rs = cursor.fetchall()

    if len(rs) > 0:
        cursor.execute("DELETE FROM comments WHERE id=?", [str(id)])
        cursor.close()
        connection.commit()
        return {"message": "El Comentario ha sido eliminado correctamente."}
    raise HTTPException(status_code=404, detail="El Comentario no se ha encontrado.")

@app.post("/comments")
async def insert(item: Comments):
    connection = sqlite3.connect('/home/tsi/equipo1/decidim_equipo1.db')
    cursor = connection.cursor()
    
    try:
        cursor.execute("INSERT INTO comments(id, id_authors, body, id_comments, created_at, down_votes, up_votes, reply_to, reply_level) \
        VALUES(?,?,?,?,?,?,?,?,?)", (item.id, item.id_authors, item.body, item.id_comments, item.created_at, item.down_votes, item.up_votes, item.reply_to, item.reply_level))

        cursor.close()
        connection.commit()
        return {"message": "El Comentario se ha guardado correctamente."}
    except:
        return HTTPException(status_code=204, detail="Hubo algun problema, revise los datos.")

@app.put("/comments/{id}")
async def update(id: int, item: Comments):
    connection = sqlite3.connect('/home/tsi/equipo1/decidim_equipo1.db')
    cursor = connection.cursor()
    #print(item.created_at)
    try:
        cursor.execute("UPDATE comments SET id_authors = ?, body = ?, id_comments = ?, created_at = ?, down_votes = ?, up_votes = ?, reply_to = ?, reply_level = ? \
        WHERE id = ?", (item.id_authors, item.body, item.id_comments, item.created_at, item.down_votes, item.up_votes, item.reply_to, item.reply_level, id))
        
        cursor.close()
        connection.commit()

        json_compatible_item_data = jsonable_encoder(item)
        return JSONResponse(content=json_compatible_item_data)
    except:
        return HTTPException(status_code=404, detail="Comentario not found")

class Proposal(BaseModel):
    id: int
    title: str
    id_authors: int
    id_comments: str
    n_endorsement: int
    created_at: datetime
    published_at: datetime

@app.get("/proposal")
async def select_all():
    connection = sqlite3.connect('/home/tsi/equipo1/decidim_equipo1.db')
    cursor = connection.cursor()

    cursor.execute("SELECT * from proposal")
    records = cursor.fetchall()
    authors = []
    columnNames = [column[0] for column in cursor.description]

    for record in records:
        authors.append( dict( zip( columnNames , record ) ) )
    json_compatible_item_data = jsonable_encoder(authors)
        
    return JSONResponse(content=json_compatible_item_data)

@app.get("/proposal/{id}")
async def select_with_condition(id: int):
    connection = sqlite3.connect('/home/tsi/equipo1/decidim_equipo1.db')
    cursor = connection.cursor()
    try:
        cursor.execute("SELECT * FROM proposal WHERE id=?", [str(id)])
        records = cursor.fetchall()
        cursor.close()
        columnNames = [column[0] for column in cursor.description]

        for record in records:
            json_compatible_item_data = jsonable_encoder(dict( zip( columnNames , record ) ) )
            
        return JSONResponse(content=json_compatible_item_data)
    except Exception:
        return HTTPException(status_code=404, detail="Item not found")

@app.delete("/proposal/{id}")
async def delete_with_condition(id: int):
    connection = sqlite3.connect('/home/tsi/equipo1/decidim_equipo1.db')
    cursor = connection.cursor()

    cursor.execute("SELECT * FROM proposal WHERE id=?", [str(id)])
    rs = cursor.fetchall()
    
    if len(rs) > 0:
        cursor.execute("DELETE FROM proposal WHERE id=?", [str(id)])
        cursor.close()
        connection.commit()
        return {"message": "La propuesta ha sido eliminada correctamente."}
    raise HTTPException(status_code=404, detail="Propuesta no encontrada.")

@app.post("/proposal")
async def insert(item: Proposal):
    connection = sqlite3.connect('/home/tsi/equipo1/decidim_equipo1.db')
    cursor = connection.cursor()

    try:
        cursor.execute("INSERT INTO proposal(id, title, id_authors, id_comments, n_endorsement, created_at, published_at) \
        VALUES(?,?,?,?,?,?,?)", (item.id, item.title, item.id_authors, item.id_comments, item.n_endorsement, item.created_at, item.published_at))
        
        cursor.close()
        connection.commit()
        return {"message": "La propuesta ha sido creada correctamente"}
    except:
        return HTTPException(status_code=404, detail="Ya exite una propuesta con esa ID, revisa los datos")
        

@app.put("/proposal/{id}")
async def update(id: int, item: Proposal):
    connection = sqlite3.connect('/home/tsi/equipo1/decidim_equipo1.db')
    cursor = connection.cursor()
    
    try:
        cursor.execute("UPDATE proposal SET title = ?, id_authors = ?, id_comments = ?, n_endorsement = ?, created_at = ?, published_at = ? \
        WHERE id = ?", (item.title, item.id_authors, item.id_comments, item.n_endorsement, item.created_at, item.published_at, id))

        cursor.close()
        connection.commit()

        json_compatible_item_data = jsonable_encoder(item)
        return JSONResponse(content=json_compatible_item_data)
    except:
        return HTTPException(status_code=404, detail="Propuesta not found")
