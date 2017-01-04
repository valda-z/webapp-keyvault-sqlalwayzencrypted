using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Description;
using WebAppKeyVaultNET.Models;

namespace WebAppKeyVaultNET.Controllers
{
    public class ToDoesController : ApiController
    {
        private ApplicationDbContext db = new ApplicationDbContext();

        // GET: api/ToDoes
        public IQueryable<ToDo> GetToDoes()
        {
            return db.ToDoes;
        }

        // GET: api/ToDoes/5
        [ResponseType(typeof(ToDo))]
        public async Task<IHttpActionResult> GetToDo(int id)
        {
            ToDo toDo = await db.ToDoes.FindAsync(id);
            if (toDo == null)
            {
                return NotFound();
            }

            return Ok(toDo);
        }

        // PUT: api/ToDoes/5
        [ResponseType(typeof(void))]
        public async Task<IHttpActionResult> PutToDo(int id, ToDo toDo)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != toDo.Id)
            {
                return BadRequest();
            }

            db.Entry(toDo).State = EntityState.Modified;

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!ToDoExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/ToDoes
        [ResponseType(typeof(ToDo))]
        public async Task<IHttpActionResult> PostToDo(ToDo toDo)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.ToDoes.Add(toDo);
            await db.SaveChangesAsync();

            return CreatedAtRoute("DefaultApi", new { id = toDo.Id }, toDo);
        }

        // DELETE: api/ToDoes/5
        [ResponseType(typeof(ToDo))]
        public async Task<IHttpActionResult> DeleteToDo(int id)
        {
            ToDo toDo = await db.ToDoes.FindAsync(id);
            if (toDo == null)
            {
                return NotFound();
            }

            db.ToDoes.Remove(toDo);
            await db.SaveChangesAsync();

            return Ok(toDo);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool ToDoExists(int id)
        {
            return db.ToDoes.Count(e => e.Id == id) > 0;
        }
    }
}