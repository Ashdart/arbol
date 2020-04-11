package com.caece.arbol.util;

public class ModelUtil {

    private ModelUtil() { throw new IllegalStateException("Utility class"); }

    public static final String CREATE_TREE_ERROR = "An error occurred trying to save the tree in database.";
    public static final String ARBOL_NOT_FOUND_ERROR = "Arbol not found with id: ";
    public static final String NODO_NOT_FOUND_ERROR = "Nodo not found with id: ";
    public static final String EMPTY_ROOT = "The current tree is empty.";
    public static final String CHILDLESS = "The current node has no children.";
    public static final String NO_BROTHER_RIGHT = "The current node does not have a brother on the right.";
    public static final String ARBOL_ALREADY_EXIST = "The tree already exist with id : ";

}
