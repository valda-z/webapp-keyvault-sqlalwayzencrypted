using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace WebAppKeyVaultNET.Models
{
    [Table("ToDo")]
    public class ToDo
    {
        [Key]
        public int Id { get; set; }

        [StringLength(500)]
        public string Note { get; set; }

        [StringLength(500)]
        [Column(TypeName = "varchar")]
        public string MySecretNote { get; set; }

    }
}