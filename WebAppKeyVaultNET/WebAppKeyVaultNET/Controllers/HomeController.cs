using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Mvc;

namespace WebAppKeyVaultNET.Controllers
{
    public class HomeController : Controller
    {
        public ActionResult Index()
        {
            Response.Redirect("Content/index.html");
            return View();
        }
    }
}
