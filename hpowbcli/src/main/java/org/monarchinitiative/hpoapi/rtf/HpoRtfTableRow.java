package org.monarchinitiative.hpoapi.rtf;

import com.github.phenomics.ontolib.formats.hpo.HpoTerm;
import com.github.phenomics.ontolib.ontology.data.TermSynonym;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HpoRtfTableRow {
    /** The level with respect to the initial term in the subhierarchy we are showing (which is defined as 1) */
    private final int level;
    /** HPO Term id as a String */
    private final String id;
    /** HPO Term label (name). */
    private final String label;
    /** Definition of the HPO term. */
    private  String definition;
    /** Comment, if any, associated with this HPO term. */
    private  String comment;
    /** List of synonyms (if any) of this HPO term. */
    private  String synonyms;


    public HpoRtfTableRow(int lev, HpoTerm term) {
        level=lev;
        id=term.getId().getIdWithPrefix();
        label=term.getName();
        definition=term.getDefinition()!=null?term.getDefinition():"\\b \\i needs definition! \\i0 \\b0";
        comment=term.getComment()!=null?term.getComment():"-";
        List<TermSynonym> synlist = term.getSynonyms();
        synonyms = synlist.stream().map(s -> s.getValue()).collect(Collectors.joining("; "));
        if (synonyms.isEmpty() || synonyms.length()==0) synonyms=" ";
    }


    public HpoRtfTableRow(int lev, HpoTerm term, String explanation) {
        this(lev,term);
        definition=explanation;
        comment=" ";
        synonyms=" ";


    }



    /**
     * @return A Table header for showing HPO terms.
     */
    public static String header() {
        return "\\trowd\n" +
                "\\cellx1000\n" +
                "\\cellx3000\n" +
                "\\cellx6000\n" +
                "\\cellx9000\n" +
                "\\b term\\b0\\intbl\\cell\n" +
                "\\b definition\\b0\\intbl\\cell\n" +
                "\\b comment\\b0\\intbl\\cell\n" +
                "\\b synonyms\\b0 \\intbl\\cell\n" +
                "\\row";
    }

    /**
     *
     * @return One RTF table row corresponding to an HPO term.
     */
    public String row() {
        String levelAndId = getLevelAndId();
        return String.format("\\trowd\n" +
                "\\cellx1000\n" +
                "\\cellx3000\n" +
                "\\cellx6000\n" +
                "\\cellx9000\n" +
                "%s\\intbl\\cell\n" +
                "%s\\intbl\\cell\n" +
                "%s\\intbl\\cell\n" +
                "%s\\intbl\\cell\n" +
                "\\row", levelAndId, definition, comment,synonyms);
    }

    /**
     * Create the contents of the first cell of the table row, something like ------- label [id].
     * @return
     */
    private String getLevelAndId() {
        char[] chars = new char[3 * level];
        char c = '-';
        Arrays.fill(chars, c);
        return String.format("%s %s [%s]", String.valueOf(chars), label, id);
    }


}
