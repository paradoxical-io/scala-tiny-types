package com.devshorts.data.typetag2


import TagTypes.@@

object TinyTypes {
   
  /* Tiny type definition for workId */

  trait TinyworkId
  type workId = String @@ TinyworkId
  implicit def workId(rawType : String) : workId =
    rawType.asInstanceOf[workId]

    

  /* Tiny type definition for funId */

  trait TinyfunId
  type funId = Int @@ TinyfunId
  implicit def toFunId(rawType : Int) : funId =
    rawType.asInstanceOf[funId]

    

  /* Tiny type definition for tableId */

  trait TinytableId
  type tableId = java.util.UUID @@ TinytableId
  implicit def tableId(rawType : java.util.UUID) : tableId =
    rawType.asInstanceOf[tableId]

    
}


object TagTypes {
    type Tagged[U] = { type Tag = U }
    type @@[T, U] = T with Tagged[U]
}
    
        