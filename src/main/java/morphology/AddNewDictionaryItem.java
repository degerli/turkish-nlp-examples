package morphology;

import zemberek.core.turkish.PrimaryPos;
import zemberek.core.turkish.RootAttribute;
import zemberek.core.turkish.SecondaryPos;
import zemberek.morphology.lexicon.DictionaryItem;
import zemberek.morphology.parser.MorphParse;
import zemberek.morphology.parser.tr.TurkishWordParserGenerator;

import java.io.IOException;
import java.util.List;


public class AddNewDictionaryItem {

    static void addProperNoun() throws IOException {
        TurkishWordParserGenerator parserGenerator = TurkishWordParserGenerator.createWithDefaults();
        String input = "Meydan'a";

        List<MorphParse> before = parserGenerator.parse(input);
        System.out.println("Parses for " + input + " before adding lemma :");
        printResults(before);

        // This must be called. So that parser forgets the old results. Due to a small bug,
        // parserGenerator.invalidateCache(input); does not work.
        parserGenerator.invalidateAllCache();

        DictionaryItem item =
                new DictionaryItem("Meydan", "meydan", "meydan", PrimaryPos.Noun, SecondaryPos.ProperNoun);
        parserGenerator.getGraph().addDictionaryItem(item);

        List<MorphParse> after = parserGenerator.parse(input);
        System.out.println("Parses for " + input + " after adding lemma :");
        printResults(after);
    }

    private static void addVerb() throws IOException {
        TurkishWordParserGenerator parserGenerator = TurkishWordParserGenerator.createWithDefaults();
        String input = "tweetleyeyazdım";
        List<MorphParse> before = parserGenerator.parse(input);
        System.out.println("Parses for " + input + " before adding lemma `tweetlemek` = " + before);
        DictionaryItem item =
                new DictionaryItem("tweetlemek", "tweetle", "tivitle", PrimaryPos.Verb, SecondaryPos.None);
        parserGenerator.getGraph().addDictionaryItem(item);
        parserGenerator.invalidateCache(input);
        List<MorphParse> after = parserGenerator.parse(input);
        System.out.println("Parses for " + input + " after adding lemma `tweetlemek` = " + after);
    }

    private static void printResults(List<MorphParse> before) {
        int i = 1;
        for (MorphParse morphParse : before) {
            String str = morphParse.formatLong();
            if (morphParse.dictionaryItem.attributes.contains(RootAttribute.Runtime)) {
                str = str + " (Generated by UnidentifiedTokenParser)";
            }
            System.out.println(i + " - " + str);
            i++;
        }
    }

    public static void main(String[] args) throws IOException {
        //addVerb();
        addProperNoun();
    }
}
