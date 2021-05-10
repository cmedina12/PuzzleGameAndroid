package coms.example.puzzlegame.net_utils;

/**
 * Code in this class is based on the Volley tutorial in the Word document
 * linked in the tutorials GitLab.
 *
 * Class for testing usage of Volley with outside urls.
 * It contains four urls to obtain information from.
 * The received information can be of type JSON (or JSON array), String, or image.
 */
public class Const {
    public static final String URL_JSON_OBJECT = "https://api.androidhive.info/volley/person_object.json";
    public static final String URL_JSON_ARRAY = "https://api.androidhive.info/volley/person_array.json";
    public static final String URL_STRING_REQ = "https://api.androidhive.info/volley/string_response.html";
    public static final String URL_IMAGE = "https://api.androidhive.info/volley/volley-image.jpg";
}
