/**
 * Copyright © 2017 Heinz&Knurre (andreas.gorbach@gmail.com christian.d.middel@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.heinz.und.knurre.blackoutpedia;

import org.junit.Test;
import org.sweble.wikitext.engine.EngineException;
import org.sweble.wikitext.engine.PageId;
import org.sweble.wikitext.engine.PageTitle;
import org.sweble.wikitext.engine.WtEngineImpl;
import org.sweble.wikitext.engine.config.WikiConfig;
import org.sweble.wikitext.engine.nodes.EngProcessedPage;
import org.sweble.wikitext.engine.output.HtmlRenderer;
import org.sweble.wikitext.engine.utils.DefaultConfigEnWp;
import org.sweble.wikitext.parser.parser.LinkTargetException;

import static org.junit.Assert.assertTrue;

public class HtmlRendererTest {

    @Test
    public void testBoeblingen() throws LinkTargetException, EngineException {

        String raw = "{{Infobox Gemeinde in Deutschland\n" +
                "|Art               = Stadt\n" +
                "|Wappen            = DEU Boeblingen COA.svg\n" +
                "|Breitengrad       = 48/41/08/N\n" +
                "|Längengrad        = 09/00/55/E\n" +
                "|Lageplan          = Böblingen in BB.svg\n" +
                "|Bundesland        = Baden-Württemberg\n" +
                "|Regierungsbezirk  = Stuttgart\n" +
                "|Landkreis         = Böblingen\n" +
                "|Höhe              = 464\n" +
                "|PLZ               = 71032, 71034\n" +
                "|Vorwahl           = 07031\n" +
                "|Gemeindeschlüssel = 08115003\n" +
                "|Adresse           = Marktplatz 16<br />71032 Böblingen\n" +
                "|Website           = [http://www.boeblingen.de/ www.boeblingen.de]\n" +
                "|Bürgermeister     = [[Wolfgang Lützner]]\n" +
                "|Bürgermeistertitel= Oberbürgermeister\n" +
                "|Partei            = CDU\n" +
                "}}\n" +
                "\n" +
                "'''Böblingen''' [{{IPA|ˈbøːblɪŋən}}] ist eine [[Kreisstadt]] im zentralen [[Baden-Württemberg]], etwa 20 Kilometer südwestlich von [[Stuttgart]]. Böblingen ist die zweitgrößte Stadt im [[Landkreis Böblingen]]. Zusammen mit [[Sindelfingen]] bildet sie ein [[Mittelzentrum]] für die umliegenden Gemeinden. Seit dem 1. Februar 1962 ist Böblingen [[Große Kreisstadt]].\n" +
                "\n" +
                "== Geographie ==\n" +
                "=== Geographische Lage ===\n" +
                "[[Datei:BöblingenTreppen.jpg|mini|hochkant|Treppenreiche Innenstadt]]\n" +
                "[[Datei:BoeblingenMarktplatz.jpg|mini|Der Marktplatz]]\n" +
                "[[Datei:BoeblingenRathaus.jpg|mini|Das 1952 erbaute alte Rathaus]]\n" +
                "\n" +
                "Böblingen liegt am Nordostrand des [[Obere Gäue|Oberen Gäus]], auf und an einem steilen Hügel, der ein Ausläufer des [[Schönbuch]]s ist. Der nördliche [[Schwarzwald]] ist von Böblingen aus in etwa einer halben Stunde, die [[Schwäbische Alb]] in 40 Minuten erreichbar.\n" +
                "\n" +
                "=== Nachbargemeinden ===\n" +
                "Folgende Städte und Gemeinden grenzen an die Stadt Böblingen. Sie werden im [[Uhrzeigersinn]] beginnend im Osten genannt: [[Leinfelden-Echterdingen]] (Landkreis Esslingen) sowie [[Schönaich]], [[Holzgerlingen]], [[Ehningen]] und [[Sindelfingen]] (alle Landkreis Böblingen)\n" +
                "\n" +
                "=== Stadtgliederung ===\n" +
                "Böblingen besteht aus der [[Kernstadt]] und dem im Rahmen der [[Gebietsreform]] am 1. September 1971 eingegliederten [[Stadtteil]] [[Dagersheim]]. In Dagersheim gibt es einen von der Bevölkerung bei jeder Kommunalwahl neu zu wählenden [[Ortschaftsrat]], dem ein [[Ortsvorsteher]] vorsteht.\n" +
                "\n" +
                "In der Kernstadt werden zum Teil Wohngebiete mit eigenem Namen unterschieden, deren Bezeichnungen sich im Laufe der Geschichte aufgrund der Bebauung ergeben haben, die jedoch meist nicht genau abgrenzbar sind. Hierzu gehören beispielsweise Tannenberg, Waldburg, Rauher Kapf, Grund und Diezenhalde.\n" +
                "\n" +
                "=== Raumplanung ===\n" +
                "Böblingen bildet zusammen mit der Nachbarstadt Sindelfingen ein [[Mittelzentrum]] innerhalb der [[Region Stuttgart]], deren [[Oberzentrum]] Stuttgart ist. Zum Mittelbereich Böblingen/Sindelfingen gehören neben den beiden Städten noch die Gemeinden im mittleren Teil des Landkreises Böblingen, und zwar [[Aidlingen]], [[Altdorf (Landkreis Böblingen)|Altdorf]], [[Ehningen]], [[Gärtringen]], [[Grafenau (Württemberg)|Grafenau]], [[Hildrizhausen]], [[Holzgerlingen]], [[Magstadt]], [[Schönaich]], [[Steinenbronn]], [[Waldenbuch]] und [[Weil im Schönbuch]].\n" +
                "\n" +
                "=== Flächenaufteilung ===\n" +
                "<timeline>\n" +
                "ImageSize = width:600 height:150\n" +
                "PlotArea  = width:90% height:66% bottom:25% left:5%\n" +
                "DateFormat = x.y\n" +
                "Period = from:0 till:100\n" +
                "Legend = columns:2 columnwidth:250 left:12% top:95%\n" +
                "TimeAxis  = orientation:horizontal\n" +
                "ScaleMajor = unit:year increment:10 start:0\n" +
                "\n" +
                "# The formatting blanks in the Colors-section are in fact figure spaces (U+2007)\n" +
                "Colors =\n" +
                "  id:Gesa   value:white        legend:    3904_ha Gesamtfläche\n" +
                "  id:Wald   value:teal         legend:1854_ha = 47,5_% Waldfläche\n" +
                "  id:Land   value:yelloworange legend: 540_ha = 13,8_% Landwirtschaftsfläche\n" +
                "  id:Wass   value:brightblue   legend:  14_ha =  0,4_% Wasserfläche\n" +
                "  id:Erho   value:green        legend:  91_ha =  2,3_% Erholungsfläche\n" +
                "  id:Geba   value:red          legend: 960_ha = 24,6_% Gebäude-_und_Freifläche\n" +
                "  id:Verk   value:tan1         legend: 379_ha =  9,7_% Verkehrsfläche\n" +
                "  id:Sons   value:purple       legend:  67_ha =  1,7_% Sonstige_Flächen\n" +
                "\n" +
                "PlotData =\n" +
                " from:00.0 till:47.5 color:Wald\n" +
                " from:47.5 till:61.3 color:Land\n" +
                " from:61.3 till:61.7 color:Wass\n" +
                " from:61.7 till:64.0 color:Erho\n" +
                " from:64.0 till:88.6 color:Geba\n" +
                " from:88.6 till:98.3 color:Verk\n" +
                " from:98.3 till:100.0 color:Sons\n" +
                "</timeline>\n" +
                "\n" +
                "Nach Daten des [[Statistisches Landesamt Baden-Württemberg|Statistischen Landesamtes]], Stand 2014.<ref>[https://www.statistik-bw.de/BevoelkGebiet/GebietFlaeche/015152xx.tab?R=GS115003 Statistisches Landesamt, Fläche seit 1988 nach tatsächlicher Nutzung] für Böblingen.</ref>\n" +
                "<!-- === Klima === -->\n" +
                "\n" +
                "== Geschichte ==\n" +
                "=== Historie ===\n" +
                "Die Besiedlung des Böblinger Raumes reicht zurück in die [[Altsteinzeit]] (ca. 25.000–20.000 v. Chr.). Reste eines hier gefundenen Mammuts weisen Spuren menschlicher Bearbeitung auf. Besiedlungsspuren und [[Hügelgrab|Hügelgräber]] stammen aus der [[Bronzezeit]] (ca. 1100 v. Chr.) und der [[Kelten]]zeit (späte Hallstatt- bzw. Latènezeit; ca. 400 v. Chr.). Die ersten schriftlichen Überlieferungen stammen aus dem Mittelalter um 1100 n. Chr.: „Bebelingen“ wird darin als Name eines [[Alamannen|alemannischen]] Adelsgeschlechts erwähnt. Auf die Alemannen weist die Endsilbe [[-ingen]] im Namen von Böblingen. Der erste Teil des Stadtnamens wird mit einem Adeligen namens „Bobilo“ in Verbindung gebracht.\n" +
                "\n" +
                "Im Jahr 1272 residierte in Böblingen eine Seitenlinie der [[Pfalzgrafen von Tübingen]], auf die die Stadtgründung zurückgeht. Sie planten die Stadt in Gestalt eines halben Ovals um den Schlossberg, mit der Marktstraße als Längsachse und rechtwinklig dazu verlaufenden Quergassen. Die Herrschaft der Tübinger Pfalzgrafen endete 1344 bzw. 1357, als wirtschaftlicher Niedergang sie zwang, die Stadt an die [[Haus Württemberg|Grafen von Württemberg]] zu veräußern. Böblingen wurde dadurch Sitz eines württembergischen „Amtes“ und später eines „[[Oberamt (Württemberg)|Oberamtes]]“.\n" +
                "\n" +
                "[[Datei:Böblingen, Andreas Kieser.png|mini|hochkant=1.5|Böblingen 1681, Forstlagerbuch von [[Andreas Kieser]]]]\n" +
                "[[Datei:Boeblingen-um1900.jpg|mini|Böblingen um 1900]]\n" +
                "[[Datei:Böblingen 01 1939-07-01.jpg|mini|Böblingen vor dem Zweiten Weltkrieg]]\n" +
                "\n" +
                "Am Ort des Böblinger Schlosses stand vorher eine Burg. Ihr Bau wird aufgrund von Keramikfunden auf das 7. bis 9. Jahrhundert datiert. Urkundlich erwähnt wurde die Burg erstmals 1302. Das Schloss wurde im 15. Jahrhundert Witwensitz des Hauses Württemberg. Die bedeutendsten der fürstlichen Witwen, die dort residierten, waren Gräfin [[Mechthild von der Pfalz]], die Mutter des Grafen Eberhard im Bart von Württemberg, und [[Barbara Gonzaga]] von [[Mantua]], die Gemahlin Eberhards. Bis zur Zerstörung im Zweiten Weltkrieg prägte das Schloss das Stadtbild.\n" +
                "\n" +
                "Am 12. Mai 1525 war Böblingen Schauplatz einer der blutigsten Schlachten des [[Deutscher Bauernkrieg|Deutschen Bauernkriegs]]. [[Georg Truchsess von Waldburg-Zeil]], Feldherr des „[[Schwäbischer Bund|Schwäbischen Bundes]]“, schlug dabei 15.000 Bauern aus Württemberg, dem Schwarzwald und dem Hegau.\n" +
                "\n" +
                "1850 hatte Böblingen 3665 evangelische Einwohner, neun bekannten sich zum katholischen und sieben zum jüdischen Glauben. Sie lebten und arbeiteten in 482 Haupt- und 126 Nebengebäuden.<ref name=\"OAB\">[[s:Beschreibung des Oberamts Böblingen#Tabelle I.|Beschreibung des Oberamts Böblingen – Tabelle I.]]</ref> Zu dieser Zeit waren [[Landwirtschaft|Land]]- und [[Forstwirtschaft]] noch die wichtigsten Erwerbsquellen; 35 % der Markungsfläche wurden landwirtschaftlich genutzt und 58 % waren bewaldet. Neben dem Handwerk und der Weberei, die im Umbruch von der handwerklichen zur industriellen Fertigung begriffen war, entstanden erste Industriebetriebe wie das Laboratorium des Apothekers Bonz, das sich im 19. Jahrhundert zu einer Fabrik von Weltrang für chemische Erzeugnisse, besonders [[Anästhetikum|Narkosemittel]] entwickelte, sowie Spinnereien und die Spielwarenfabrik von Christian Auberlen.\n" +
                "\n" +
                "Beim Aufbruch ins Industriezeitalter war das vielfältige Handwerk, der Fleiß, die Geschicklichkeit und der Einfallsreichtum der Menschen ein Standortvorteil. Die Maschinenfabrik August Wagner (Bau von Pressen, Nähmaschinen, Dampfmaschinen und Fasswaschmaschinen für Brauereien) wurden Musterbeispiele für schwäbisches Tüftlertum. Den entscheidenden Impuls erhielt die Böblinger Industrieentwicklung 1879 mit der Eröffnung der [[Gäubahn (Stuttgart–Singen)|Gäubahn]]. Damit einher ging die Ausweisung neuer Industriegebiete. Schon 1886 siedelte sich dort die Mechanische Trikotweberei Ludwig Maier u. Genieder an. Das unternehmerische Erfolgskonzept bestand in der Produktion des neu auf den Markt gekommenen Büstenhalters.\n" +
                "\n" +
                "Im [[Erster Weltkrieg|Ersten Weltkrieg]] wurde am 16. August 1915 der Böblinger Militärflugplatz eingeweiht. Darauf folgend war für die weitere Stadtentwicklung von entscheidender Bedeutung, dass Böblingen 1925 Sitz des [[Flughafen Böblingen|Landesflughafens]] für Württemberg wurde. Böblingen war die „Brücke zur Welt“. Am Rande des Flugplatzes errichtete der Böblinger Luftfahrtpionier [[Hanns Klemm]] (1885–1961) Ende 1926 seine Firma „Leichtflugzeugbau Klemm“. Diese wurde bis in den Zweiten Weltkrieg hinein wichtigster Industriebetrieb der Stadt.\n" +
                "\n" +
                "Auch eine Dampf-Ziegelei war einst in Böblingen angesiedelt. Hier wurden unter der Leitung von Prokurist Alois Reinold zahlreiche Dachziegel gefertigt, die noch heute die Dächer der älteren Häuser in Böblingen und Sindelfingen bedecken.\n" +
                "\n" +
                "[[Datei:Böblingen 04 1943-10-12.jpg|mini|Zentrum nach dem Bombenangriff vom 7./8. Oktober 1943]]\n" +
                "\n" +
                "Der Luftangriff durch Luftstreitkräfte der [[Alliierte]]n in der Nacht vom 7. auf den 8. Oktober 1943 zerstörte den größten Teil der Altstadt mit der Stadtkirche, dem Schloss und dem Rathaus. Es gab zahlreiche Tote und Verletzte. Durch diesen und weitere Bombenangriffe waren bei Kriegsende ca. 40 Prozent der bebauten Fläche zerstört und nahezu 2.000 Menschen obdachlos.\n" +
                "\n" +
                "Nach der Währungsreform vom 20. Juni 1948 begann ein dynamischer Wiederaufbau. Die Einwohnerzahl verdreifachte sich innerhalb von nur zwei Jahrzehnten (1950: 12.600; 1970: 37.500). Mit der Ansiedlung zukunftsorientierter Firmen wie [[IBM]] (1949) und [[Hewlett-Packard]] (1959) sowie von mittelständischen Betrieben, die sich seit den siebziger Jahren vor allem auch auf der ''Hulb''<ref>http://www.boeblingen.de/,Lde/start/StadtIdeen/Die+Hulb.html</ref> niederließen, setzte parallel zur Bevölkerungszunahme ein starkes Wirtschaftswachstum ein.\n" +
                "\n" +
                "Die Einwohnerzahl Böblingens überschritt 1957 die Grenze von 20.000. Daraufhin stellte die Stadtverwaltung den Antrag auf Erhebung zur [[Große Kreisstadt|Großen Kreisstadt]], was die [[Landesregierung von Baden-Württemberg|baden-württembergische Landesregierung]] dann mit Wirkung vom 1. Februar 1962 beschloss.\n" +
                "\n" +
                "Der Plan zur Zusammenlegung mit der Nachbarstadt [[Sindelfingen]] im Rahmen der [[Gemeindereform]] zu Beginn der siebziger Jahre scheiterte am Widerstand der Bevölkerung.\n" +
                "\n" +
                "Vom 19. bis 27. Juli 1981 wurden in Böblingen die VII. [[Internationale Feuerwehrwettkämpfe|Internationalen Feuerwehrwettkämpfe]] des [[CTIF]] (Feuerwehrolympiade) durchgeführt.\n" +
                "\n" +
                "1996 richtete Böblingen eine [[Landesgartenschau]] aus, die zu einer Aufwertung der innerstädtischen Grünanlagen genutzt wurde.\n" +
                "\n" +
                "Am 25. Mai 2009 erhielt die Stadt den von der [[Bundesregierung (Deutschland)|Bundesregierung]] verliehenen Titel „[[Ort der Vielfalt]]“.\n" +
                "\n" +
                "=== Religionen ===\n" +
                "[[Datei:Böblingen 07 1943-10-12.jpg|mini|hochkant|Marktplatz und ev. Stadtkirche nach dem Bombenangriff 1943]]\n" +
                "[[Datei:BöblingenMarktplatz.JPG|mini|hochkant|Marktplatz mit Stadtkirche 2006]]\n" +
                "\n" +
                "Die Bevölkerung von Böblingen gehörte ursprünglich zum [[Bistum Konstanz]] und war dem [[Archidiakonat]] „ante nemus“ unterstellt. Da die Stadt schon früh zu [[Württemberg]] gehörte, wurde auch hier ab 1535 durch Herzog [[Ulrich (Württemberg)|Ulrich]] die [[Reformation]] eingeführt, daher war Böblingen über Jahrhunderte eine überwiegend protestantische Stadt. In jener Zeit wurde die Stadt Sitz eines [[Dekanat]]s (siehe [[Kirchenbezirk Böblingen]]), dessen Dekanatskirche die Stadtkirche ist. Die Kirchengemeinde Böblingen wuchs nach dem [[Zweiter Weltkrieg|Zweiten Weltkrieg]] infolge Zuzugs stark an und wurde daher geteilt. Es entstanden die Martin-Luther-Gemeinde (Kirche von 1960) und die Paul-Gerhardt-Gemeinde (Kirche von 1962) sowie 1990 im Wohngebiet Diezenhalde ein ökumenisches Gemeindezentrum mit der evangelischen Christuskirche und der katholischen Kirchengemeinde „Vater unser“. Die vier evangelischen Kirchengemeinden (Stadt-, Martin-Luther-, Paul-Gerhardt- und Christuskirche) bilden zusammen die Evangelische Gesamtkirchengemeinde Böblingen. Auch im Stadtteil Dagersheim wurde infolge der frühen Zugehörigkeit zu Württemberg die Reformation eingeführt. Die dortige evangelische Kirchengemeinde feiert ihre Gottesdienste in der im 15. Jahrhundert erbauten Agathenkirche, die seit 1476 der Universität Tübingen gehörte. Die Gemeinde gehört wie alle Böblinger Kirchengemeinden zum Dekanat Böblingen innerhalb der [[Evangelische Landeskirche in Württemberg|Evangelischen Landeskirche in Württemberg]].\n" +
                "\n" +
                "Katholiken gibt es in Böblingen erst wieder seit dem späten 19. Jahrhundert. Für sie wurde 1895 bis 1899 eine eigene Kirche St. Bonifatius gebaut. 1910 wurde Böblingen eigene [[Pfarrei]]. Eine zweite katholische Kirche (St. Klemens) wurde 1959 erbaut, die 1961 zur Pfarrei erhoben wurde. 1963 wurde die Kirche St. Maria erbaut und 1965 zur Pfarrei erhoben. 1990 entstand dann noch die „Vater unser“-Gemeinde in der Diezenhalde als Ökumenisches Gemeindezentrum mit der Evangelischen Kirche. Alle Gemeinden bilden die gemeinsame Seelsorgeeinheit 2. Im Stadtteil Dagersheim wurde 1958 die Kirche Christkönig erbaut und 1961 zur Pfarrei erhoben. Diese Gemeinde bildet zusammen mit den Sindelfinger Gemeinden „Auferstehung Christi“, „Maria Königin des Friedens“ und „Zur Heiligsten Dreifaltigkeit“ eine gemeinsame Seelsorgeeinheit 9. Beide Seelsorgeeinheiten gehören zum Dekanat Böblingen des [[Bistum Rottenburg-Stuttgart|Bistums Rottenburg-Stuttgart]].\n" +
                "\n" +
                "Neben den beiden großen Kirchen gibt es in Böblingen auch [[Freikirche]]n und Gemeinden, darunter die [[Süddeutscher Gemeinschaftsverband|Süddeutsche Gemeinschaft]], die [[Evangelisch-methodistische Kirche]], die [[Evangelisch-Freikirchliche Gemeinde]] ([[Baptisten]]), die [[Bund Freier evangelischer Gemeinden in Deutschland|Freie evangelische Gemeinde]], die [[Adventgemeinde]] und die [[Volksmission entschiedener Christen]] e.&nbsp;V.\n" +
                "\n" +
                "Auch die [[Neuapostolische Kirche]] und die [[Zeugen Jehovas]] sind in Böblingen vertreten.\n" +
                "\n" +
                "=== Eingemeindungen ===\n" +
                "Am 1. September 1971 wurde die bis dahin eigenständige Gemeinde Dagersheim eingegliedert.<ref>{{BibISBN|3170032631|Seite=447}}</ref>\n" +
                "\n" +
                "=== Einwohnerentwicklung ===\n" +
                "Die Einwohnerzahlen sind Schätzungen, Volkszählungsergebnisse (¹) oder amtliche Fortschreibungen der jeweiligen Statistischen Ämter (nur [[Wohnsitz (Deutschland)#Haupt- und Zweitwohnsitz|Hauptwohnsitze]]).\n" +
                "{| border=\"0\"\n" +
                "| valign=\"top\" |\n" +
                "{| class=\"wikitable\"\n" +
                "! Jahr\n" +
                "! Einwohner\n" +
                "|-\n" +
                "| 1598 || align=\"right\" | ca. 800\n" +
                "|-\n" +
                "| 1654 || align=\"right\" | 628\n" +
                "|-\n" +
                "| 1803 || align=\"right\" | 2.125\n" +
                "|-\n" +
                "| 1823 || align=\"right\" | 2.549\n" +
                "|-\n" +
                "| 1843 || align=\"right\" | 3.504\n" +
                "|-\n" +
                "| 1850<ref name=\"OAB\" /> || align=\"right\" | 3.681\n" +
                "|-\n" +
                "| 1861 || align=\"right\" | 3.287\n" +
                "|-\n" +
                "| 1. Dezember 1871 || align=\"right\" | 3.826\n" +
                "|-\n" +
                "| 1. Dezember 1880 ¹ || align=\"right\" | 4.365\n" +
                "|}\n" +
                "| valign=\"top\" |\n" +
                "{| class=\"wikitable\"\n" +
                "! Jahr\n" +
                "! Einwohner\n" +
                "|-\n" +
                "| 1. Dezember 1890 ¹ || align=\"right\" | 4.659\n" +
                "|-\n" +
                "| 1. Dezember 1900 ¹ || align=\"right\" | 5.303\n" +
                "|-\n" +
                "| 1. Dezember 1910 ¹ || align=\"right\" | 6.019\n" +
                "|-\n" +
                "| 16. Juni 1925 ¹ || align=\"right\" | 7.227\n" +
                "|-\n" +
                "| 16. Juni 1933 ¹ || align=\"right\" | 7.998\n" +
                "|-\n" +
                "| 17. Mai 1939 ¹ || align=\"right\" | 12.560\n" +
                "|-\n" +
                "| 1946 || align=\"right\" | 10.809\n" +
                "|-\n" +
                "| 13. September 1950 ¹ || align=\"right\" | 12.601\n" +
                "|-\n" +
                "| 6. Juni 1961 ¹ || align=\"right\" | 25.366\n" +
                "|}\n" +
                "| valign=\"top\" |\n" +
                "{| class=\"wikitable\"\n" +
                "! Jahr\n" +
                "! Einwohner\n" +
                "|-\n" +
                "| 27. Mai 1970 ¹ || align=\"right\" | 35.925\n" +
                "|-\n" +
                "| 31. Dezember 1975 || align=\"right\" | 40.547\n" +
                "|-\n" +
                "| 31. Dezember 1980 || align=\"right\" | 41.505\n" +
                "|-\n" +
                "| 27. Mai 1987 ¹ || align=\"right\" | 42.589\n" +
                "|-\n" +
                "| 31. Dezember 1990 || align=\"right\" | 44.903\n" +
                "|-\n" +
                "| 31. Dezember 1995 || align=\"right\" | 46.516\n" +
                "|-\n" +
                "| 31. Dezember 2000 || align=\"right\" | 45.637\n" +
                "|-\n" +
                "| 31. Dezember 2005 || align=\"right\" | 46.381\n" +
                "|-\n" +
                "| 31. Dezember 2010 || align=\"right\" | 46.488 <!-- Quelle: Statistisches Landesamt B-W. -->\n" +
                "|-\n" +
                "| 31. Dezember 2015 || align=\"right\" | 48.696\n" +
                "|}\n" +
                "|}\n" +
                "¹ <small>Volkszählungsergebnis</small>\n" +
                "\n" +
                "== Politik ==\n" +
                "=== Gemeinderat ===\n" +
                "{{Sitzverteilung\n" +
                "| Beschriftung = Sitze\n" +
                "| Land = DE\n" +
                "| float = right\n" +
                "|SPD|GRÜNE|FW(BW)|FDP|CDU|\n" +
                "| SPD = 7\n" +
                "| GRÜNE = 5\n" +
                "| FW(BW) = 8\n" +
                "| FDP = 2\n" +
                "| CDU = 11\n" +
                "}}\n" +
                "In Böblingen wird der Gemeinderat nach dem Verfahren der [[Unechte Teilortswahl|unechten Teilortswahl]] gewählt. Dabei kann sich die Zahl der Gemeinderäte durch [[Überhangmandat]]e verändern. Der Gemeinderat in Böblingen hat nach der letzten Wahl 33 Mitglieder (vorher 34). Die [[Kommunalwahlen in Baden-Württemberg 2014|Kommunalwahl am 25. Mai 2014]] führte zu folgendem amtlichen Endergebnis<ref>[http://www.kdrs.de/pb/site/kdrs/node/3190/Lde/index.html Wahlinformationen des Kommunalen Rechenzentrums Stuttgart]</ref>. Der Gemeinderat besteht aus den gewählten ehrenamtlichen Gemeinderäten und dem Oberbürgermeister als Vorsitzendem. Der Oberbürgermeister ist im Gemeinderat stimmberechtigt.\n" +
                "\n" +
                "{| class=\"wikitable\"\n" +
                "|- class=\"hintergrundfarbe5\"\n" +
                "|colspan=\"2\" | '''Parteien und Wählergemeinschaften'''\n" +
                "|align=\"center\" | '''%<br />2014'''\n" +
                "|align=\"center\" | '''Sitze<br />2014'''\n" +
                "|align=\"center\" | '''%<br />2009'''\n" +
                "|align=\"Center\" | '''Sitze<br />2009'''\n" +
                "|rowspan=\"10\" |{{Wahldiagramm\n" +
                "|LAND = DE\n" +
                "|TITEL = Kommunalwahl 2014\n" +
                "|JAHRALT = 2009\n" +
                "|JAHRNEU = 2014\n" +
                "|GUV = ja\n" +
                "|PARTEI1        = CDU\n" +
                "|ERGEBNIS1      = 32.35\n" +
                "|ERGEBNISALT1   = 31.37\n" +
                "|PARTEI2        = FW(BW)\n" +
                "|ERGEBNIS2      = 23.41\n" +
                "|ERGEBNISALT2   = 21.50\n" +
                "|PARTEI3        = SPD\n" +
                "|ERGEBNIS3      = 21.41\n" +
                "|ERGEBNISALT3   = 21.23\n" +
                "|PARTEI4        = GRÜNE\n" +
                "|ERGEBNIS4      = 15.82\n" +
                "|ERGEBNISALT4   = 14.21\n" +
                "|PARTEI5        = FDP\n" +
                "|ERGEBNIS5      = 7.01\n" +
                "|ERGEBNISALT5   = 11.69\n" +
                "}}\n" +
                "|----\n" +
                "|CDU\n" +
                "|[[Christlich Demokratische Union Deutschlands]]\n" +
                "|align=\"right\" |32,35\n" +
                "|align=\"right\" |11\n" +
                "|align=\"right\" |31,37\n" +
                "|align=\"right\" |11\n" +
                "|----\n" +
                "|FW\n" +
                "|[[Freie Wähler Landesverband Baden-Württemberg|Freie Wähler Böblingen]]\n" +
                "|align=\"right\" |23,41\n" +
                "|align=\"right\" |8\n" +
                "|align=\"right\" |21,50\n" +
                "|align=\"right\" |7\n" +
                "|----\n" +
                "|SPD\n" +
                "|[[Sozialdemokratische Partei Deutschlands]]\n" +
                "|align=\"right\" |21,41\n" +
                "|align=\"right\" |7\n" +
                "|align=\"right\" |21,23\n" +
                "|align=\"right\" |7\n" +
                "|----\n" +
                "|GRÜNE\n" +
                "|[[Bündnis 90/Die Grünen]]\n" +
                "|align=\"right\" |15,82\n" +
                "|align=\"right\" |5\n" +
                "|align=\"right\" |14,21\n" +
                "|align=\"right\" |5\n" +
                "|----\n" +
                "|FDP\n" +
                "|[[Freie Demokratische Partei]]\n" +
                "|align=\"right\" |7,01\n" +
                "|align=\"right\" |2\n" +
                "|align=\"right\" |11,69\n" +
                "|align=\"right\" |4\n" +
                "|----\n" +
                "|--- class=\"hintergrundfarbe5\"\n" +
                "|colspan=\"2\" |'''gesamt'''\n" +
                "|align=\"right\" |'''100,0'''\n" +
                "|align=\"right\" |'''33'''\n" +
                "|align=\"right\" |'''100,0'''\n" +
                "|align=\"right\" |'''34'''\n" +
                "|--- class=\"hintergrundfarbe5\"\n" +
                "|colspan=\"2\" |'''Wahlbeteiligung'''\n" +
                "|colspan=\"2\" align=\"center\" |'''40,81 %'''\n" +
                "|colspan=\"2\" align=\"center\" |'''44,24 %'''\n" +
                "|}\n" +
                "\n" +
                "=== Bürgermeister ===\n" +
                "An der Spitze der Stadt Böblingen werden seit dem 14. Jahrhundert Bürgermeister und ein Rat genannt. Es gab zwei Bürgermeister, von denen einer von den Richtern aus dem Ratskollegium, der andere von den Ratsverwandten aus dem Gericht gewählt wurden.\n" +
                "\n" +
                "Seit dem 19. Jahrhundert trug das Stadtoberhaupt die Bezeichnung Stadtschultheiß, seit 1930 [[Bürgermeister]] und mit der Erhebung zur [[Große Kreisstadt|Großen Kreisstadt]] am 1. Februar 1962 lautet die Amtsbezeichnung [[Oberbürgermeister]]. Dieser wird von den Wahlberechtigten auf 8 Jahre direkt gewählt. Er ist Vorsitzender des Gemeinderats. Seine allgemeinen Stellvertreter sind der Erste Beigeordnete mit der Amtsbezeichnung Erster Bürgermeister und der Beigeordnete mit der Amtsbezeichnung Bürgermeister.\n" +
                "\n" +
                "<!-- weitere Amtsträger bitte nachtragen -->\n" +
                "* 1883–1906: Karl Staiger\n" +
                "* 1906–1919: Andreas Dingler\n" +
                "* 1919–1937: Georg Kraut\n" +
                "* 1937–1940: Röhm\n" +
                "* 1940–1945: Friedrich Nissler (in Vertretung)\n" +
                "* 1945–1946: [[Georg Hengstberger]]\n" +
                "* 1946–1948: Richard Müller\n" +
                "* 1948–1986: [[Wolfgang Brumme]]\n" +
                "* 1986–2010: [[Alexander Vogelgsang]]\n" +
                "* seit 1. April 2010: [[Wolfgang Lützner]]\n" +
                "\n" +
                "=== Wappen und Flagge ===\n" +
                "{| border=\"1\" cellpadding=\"0\" cellspacing=\"0\" class=\"wikitable\" style=\"float:right; clear:right; margin:0em 0em 1em 1em; border-style:solid; border-width: 1px\"\n" +
                "|-\n" +
                "| align=\"center\" colspan=\"2\" bgcolor=\"#CFCFCF\" | <small>Banner, Wappen und Hissflagge</small>\n" +
                "|-\n" +
                "| align=\"right\" rowspan=\"2\" | [[Datei:Banner Boeblingen.svg|63px]]\n" +
                "| align=\"left\" valign=\"top\" | [[Datei:DEU Boeblingen COA.svg|100px]]\n" +
                "|-\n" +
                "| align=\"left\" valign=\"bottom\" | [[Datei:Flagge Boeblingen.svg|100px]]\n" +
                "|}\n" +
                "Das [[Wappen]] der '''Stadt Böblingen''' zeigt in Gold eine dreilatzige rote Fahne, die auf die Herrschaft der [[Pfalzgrafen von Tübingen]] zurückgeht. Die Böblinger Stadtflagge ist, vom Wappen abgeleitet, rot-gelb. Wappen und Flagge werden schon seit vielen Jahrhunderten geführt.\n" +
                "\n" +
                "{{Siehe auch|Liste der Wappen mit dem Emblem der Pfalzgrafen von Tübingen}}\n" +
                "<div style=\"clear:left;\"></div>\n" +
                "[[Datei:Wappen Dagersheim.png|links|60px|Wappen Dagersheim]]\n" +
                "Auch der '''Ortsteil Böblingen-Dagersheim''', der bis 1971 eine selbstständige Gemeinde darstellte, besitzt ein eigenes historisches Wappen, das neben dem Stadtwappen der Kernstadt Böblingen bis heute geführt wird. Es zeigt eine gekrönte rote Schlange mit einfach gewundenem Korpus; die ausgestreckte Zunge des Tieres ist schwarz und gespalten. Den Hintergrund des Tieres bildet ein weißes dreigipfliges Gebirge, zwischen dem der mehrfach gezackte Dagersheimer Morgenstern ''(Dagersheim – „Tag-erschein!“)'' aufgeht. Die Wappenkomposition geht auf das Spätmittelalter zurück.\n" +
                "<div style=\"clear:left;\"></div>\n" +
                "\n" +
                "=== Städtepartnerschaften ===\n" +
                "Böblingen unterhält mit folgenden Städten eine [[Städtepartnerschaft]]:\n" +
                "\n" +
                ":{{FRA|#}} [[Pontoise]] (Frankreich), seit 1956\n" +
                ":{{NLD|#}} [[Geleen]], seit 2001 [[Sittard-Geleen]] (Niederlande), seit 1962\n" +
                ":{{TUR|#}} [[Bergama]] ([[Türkei]]), seit 1967\n" +
                ":{{UK|#}} [[Glenrothes]] (Region [[Fife (Schottland)|Fife]], Schottland), seit 1971\n" +
                ":{{AUT|#}} [[Krems an der Donau]] (Österreich), seit 1972\n" +
                ":{{ITA|#}} [[Alba (Piemont)|Alba]] (Italien), seit 1985\n" +
                ":{{DE-TH|#}} [[Sömmerda]] (Thüringen), seit 1988\n" +
                "\n" +
                "Anlässlich des 20-jährigen Partnerschaftsjubiläums stiftete Böblingen im Jahr 1991 der schottischen Partnerstadt Glenrothes die Statue ''The Defenceless One'' (deutsch ''Der Wehrlose''), die von dem deutschen Bildhauer und gebürtigen Böblinger [[Rudolf Christian Baisch]] (1903–1990) geschaffen wurde. Die Statue aus Bronzeguss, die auf einem Steinsockel steht, wurde im Riversidepark in Glenrothes aufgestellt.\n" +
                "\n" +
                "== Sport ==\n" +
                "Die [[SV Böblingen]] ist Böblingens größter Sportverein mit über 7000 Mitgliedern in 25 Abteilungen.\n" +
                "\n" +
                "Im März 1997 wurde „Der Wilde Süden“ gegründet. Zehn der besten [[Rock ’n’ Roll|Rock-’n’-Roll]]-Paare der A-Klasse aus ganz Baden-Württemberg schlossen sich zusammen. „Der Wilde Süden“ ist sieben Mal Weltmeister geworden.\n" +
                "\n" +
                "Von 1949 bis 1963 existierte östlich von Böblingen bei {{Coordinate|text=DMS|NS=48/41/59/N|EW=9/2/2/E|region=DE-BW|type=landmark|name=Skisprungschanze}} eine Skisprungschanze, die Kurt-Beuttler Schanze. Sie war eine 17 Meter hohe Holzkonstruktion mit einer Anlauflänge von 40 Metern. Auf ihr fanden regelmäßig Wettbewerbe statt, doch wurde sie 1963 abgerissen, weil sie nach zahlreichen schneearmen Wintern kaum genutzt wurde und verfiel. Heute ist von der einstigen Skisprunganlage nur noch der Auslaufhang vorhanden, an dessen Ende sich ein Grillplatz befindet.[http://www.szbz.de/nachrichten/artikel/detail/weite-saetze-und-harte-landungen-9-2-2013/]\n" +
                "\n" +
                "[[Datei:Skisprunganlage Boeblingen21102016 3.png|mini|Auslauf der einstigen Kurt-Beuttler Schanze]]\n" +
                "\n" +
                "== Wirtschaft und Infrastruktur ==\n" +
                "=== Verkehr ===\n" +
                "[[Datei:BoeblingenBusbahnhof P1250397.jpg|mini|Busbahnhof]]\n" +
                "Böblingen ist auf allen Verkehrswegen gut erreichbar: Den Flughafen Stuttgart kann man mit der S-Bahn in etwa 20 Minuten erreichen.\n" +
                "\n" +
                "Der Kreuzungspunkt zwischen den [[Autobahn (Deutschland)|Bundesautobahnen]] [[Bundesautobahn 8|8]] und [[Bundesautobahn 81|81]] (Karlsruhe–München / Singen–Heilbronn) liegt unweit nordöstlich von Böblingen. Im Norden des Stadtgebiets führt die A&nbsp;81 vorbei. Über die Anschlussstellen Böblingen-Ost, Böblingen/Sindelfingen, Böblingen-Hulb und Ehningen ist die Stadt zu erreichen. Die [[Bundesstraße 464]] ([[Renningen]]–[[Reutlingen]]) und die [[Bundesstraße 14|B&nbsp;14]] führen an Böblingen vorbei.\n" +
                "\n" +
                "Böblingen liegt an der [[Gäubahn (Stuttgart–Singen)|Gäubahn]] von [[Stuttgart]] nach [[Singen (Hohentwiel)|Singen]] und wird stündlich mit Regional- und Stadtexpress-Zügen bedient. Zusätzlich ist Böblingen mit den Linien S1 [[Kirchheim (Teck)]]–[[Stuttgart]]–[[Herrenberg]] und S60 [[Renningen]]–Böblingen an das [[S-Bahn Stuttgart|S-Bahn-Netz Stuttgart]] angeschlossen. Man erreicht Stuttgart in ca. 25 Minuten. Im Jahre 1996 wurde die [[Schönbuchbahn]] nach [[Dettenhausen]] reaktiviert. Eine [[Bahnstrecke Schönaicher First–Schönaich|Zweigstrecke von der heutigen Station Böblingen Zimmerschlag nach Schönaich]] war zwischen 1922 und 1959 in Betrieb. Die [[Rankbachbahn]] nach Renningen wurde 2012 als S-Bahn-Linie S60 in Betrieb genommen. Neben dem [[Bahnhof Böblingen]] gibt es folgende weitere Haltestellen auf Böblinger Gemarkung:<br />S-Bahn: Goldberg und Hulb; Schönbuchbahn: Danziger Straße, Böblingen-Süd, Heusteigstraße und Zimmerschlag.\n" +
                "\n" +
                "Ferner verkehren im Stadtgebiet zahlreiche Buslinien, neben dem Stadtverkehr der Firma [[Pflieger]] gibt es mehrere Überlandlinien. Alle Linien verkehren zu einheitlichen Preisen innerhalb des [[Verkehrs- und Tarifverbund Stuttgart]] (VVS).\n" +
                "\n" +
                "Im Fernbusnetz bestehen unter anderem Anschlüsse nach Neu-Ulm und München.\n" +
                "\n" +
                "=== Ortsansässige Unternehmen ===\n" +
                "[[Datei:Nachtaufnahme Softwarezentrum web.jpg|mini|hochkant|[[Softwarezentrum Böblingen/Sindelfingen]] mit 98 IT-Unternehmen]]\n" +
                "* Computerindustrie: Compart, [[Agilent]], [[Hewlett-Packard]], [[Keysight Technologies]], [[IBM Deutschland Research & Development|IBM]], [[Kroll Ontrack]], [[Microsoft]], [[Philips Deutschland]], [[Siemens]], [[Softpro]], [[Softwarezentrum Böblingen/Sindelfingen]], [[Verigy]]\n" +
                "* Automobilindustrie: [[Daimler AG]], [[Smart (Automarke)|Smart]], [[Lear Corporation|Lear]]\n" +
                "* Chemische Industrie: [[Schill + Seilacher]]\n" +
                "* Medien: [[C&L Verlag]]\n" +
                "* Luft- und Raumfahrtindustrie: [[Moog (Böblingen)|Moog GmbH]]\n" +
                "* Finanzdienstleistungen: [[Kreissparkasse Böblingen]], [[Vereinigte Volksbank (Sindelfingen)|Vereinigte Volksbank eG]]\n" +
                "* Getränkeindustrie: [[Schönbuch Bräu]]\n" +
                "* Solarindustrie: AXITEC GmbH\n" +
                "* Anlagenbau: [[Eisenmann AG]]\n" +
                "* Handel: [[Plana Küchenland]]\n" +
                "* Energieversorgung: [[Stadtwerke Böblingen]]\n" +
                "\n" +
                "=== Medien ===\n" +
                "Der Regionalfernsehsender [[Regio TV Stuttgart]] hatte bis 2010 seinen Sitz in Böblingen. Dieser strahlt täglich in einer halbstündigen Rotation ab 18 Uhr bis 2 Uhr nachts das Regionalmagazin aus, mit Nachrichten und Beiträgen aus den Landkreisen Böblingen, Rems-Murr, Ludwigsburg, Göppingen und Stuttgart. In Böblingen erscheinen als [[Tageszeitung]]en die [[Kreiszeitung Böblinger Bote]], die [[Böblinger Zeitung]], eine Lokalausgabe der Sindelfinger Zeitung sowie die überregionalen Tageszeitungen [[Stuttgarter Zeitung]] und die [[Stuttgarter Nachrichten]] mit je einem Lokalteil für die Stadt und den Kreis Böblingen. Zudem erscheint jeden Monat das PIG Stadtmagazin Böblingen/Sindelfingen.\n" +
                "\n" +
                "=== Einzelhandel (überörtlich bedeutsam) ===\n" +
                "\n" +
                "Böblingen hat spätestens seit Fertigstellung des ''Kaufzentrums'' zwischen Altstadt und Bahnhof in den 60er Jahren als Einkaufsstadt Bedeutung auch für umliegende Gemeinden (und das gleich große Sindelfingen). Kern des ersten Kaufzentrums war ein [[Hertie Waren- und Kaufhaus GmbH|Hertie]]-Warenhaus. Ende der 70er-Jahre kam ein weiterer Gebäudekomplex, das ''city center'', hinzu. Die Gewichte für die Kunden aus der Region verschoben sich deutlich mit der Eröffnung des [[Breuninger|BreuningerLandes]] in Sindelfingen. Ende 2014 wurde am Bahnhof – in der ''Unterstadt'' – das [[Einkaufszentrum]] ''Mercaden'' eröffnet.<ref name=\"Mercade1\">{{Internetquelle | url=http://www.stuttgarter-nachrichten.de/inhalt.mercaden-boeblingen-wird-zur-einkaufsstadt.2c8fe961-8326-4ea7-8821-b5d7d851dfb0.html | titel=Mercaden: Böblingen (…) | autor=Ulrich Hanselmann | hrsg=stuttgarter-nachrichten.de | datum=2012-06-22 | zugriff=2014-01-16}}</ref><ref name=\"Mercade2\">{{Internetquelle | url=http://www.stuttgarter-zeitung.de/inhalt.spatenstich-boeblingen-wird-wieder-einkaufsstadt.2ab8ca5b-c449-477a-bb53-b496b005b095.html | titel=Spatenstich – Böblingen wird wieder Einkaufsstadt | autor=Gerlinde Wicke-Naber | hrsg=stuttgarter-zeitung.de | datum=2012-11-08 | zugriff=2014-01-16}}</ref>\n" +
                "Im Frühling 2015 wurde die zur Fußgängerzone umgestaltete Bahnhofstraße als „Flaniermeile“ neu eröffnet.<ref name=\"Flaniermeile\">{{Internetquelle | url=http://www.boeblingen.de/site/Boeblingen-Internet/node/11031428/Lde | titel=Böblingen feiert die Eröffnung der neuen Fußgängerzone | hrsg=Stadt Böblingen | datum=2015-04-30 | zugriff=2016-06-19}}</ref>\n" +
                "\n" +
                "=== Behörden, Gericht und Einrichtungen ===\n" +
                "Als Kreisstadt beherbergt Böblingen das Landratsamt und den Großteil der Dienststellen des Kreises. Böblingen verfügt auch über ein [[Finanzamt]], ein [[Notariat]] und ein [[Amtsgericht]], das zum [[Landgericht]]s- und [[Oberlandesgericht|OLG]]-Bezirk [[Stuttgart]] gehört.\n" +
                "\n" +
                "Die Stadt ist auch Sitz des Kirchenbezirks Böblingen der [[Evangelische Landeskirche in Württemberg|Evangelischen Landeskirche in Württemberg]] und des Dekanats Böblingen des [[Bistum Rottenburg-Stuttgart|Bistums Rottenburg-Stuttgart]].\n" +
                "\n" +
                "[[Bundespolizei (Deutschland)#Stuttgart|Bundespolizeidirektion Stuttgart]] mit Sitz in Böblingen (in der ehemaligen [[Eberhard Wildermuth|Wildermuth]]-Kaserne). Der Zuständigkeitsbereich erstreckt sich auf das Bundesland Baden-Württemberg. Sie gewährleistet mit ihren sieben unterstellten Bundespolizeiinspektionen u.a. den Schutz von Bahnanlagen, der Schengenbinnengrenze zu Frankreich und Schweiz, des [[Flughafen Stuttgart|Flughafens Stuttgart]] sowie des [[Bundesverfassungsgericht]]es.\n" +
                "\n" +
                "Überregionale Dienststellen der Polizei Baden-Württemberg – mit Sitz in Böblingen – sind die 5. Bereitschaftspolizeidirektion Böblingen und das [[Landespolizeiorchester Baden-Württemberg]]\n" +
                "\n" +
                "=== Militär ===\n" +
                "In Böblingen besteht eine [[Kaserne]] der US-amerikanischen Streitkräfte ([[Panzerkaserne Böblingen]]), in der [[United States Army Special Forces Command (Airborne)|Green Berets]] der „10th Special Forces Group, 1st Battalion (Airborne)“ stationiert sind. Außerdem ist sie das Hauptquartier der [[United States Marine Corps Forces Europe]]. (Siehe auch: [[ausländische Militärbasen in Deutschland]]). Die amerikanische Basis am alten Flughafen, ehemals eine Reparaturstätte ({{enS|Maintenance Facility}}) für militärische Kfz aller Art innerhalb des [[VII. US-Korps]] und Zweigstelle der Panzerkaserne, die zusammen die sogenannte ''Boblingen-Sindelfingen Military Community'' bildeten, wurde dagegen bereits im Herbst 1991 geschlossen. Das Gelände wird zurzeit neu verwertet.\n" +
                "Am 7. Februar 2007 wurde in der Böblinger Panzerkaserne Deutschlands größter amerikanischer Supermarkt, „Panzer-Exchange“ eröffnet. Auf 13.200&nbsp;m² können sich ausschließlich Truppen- und NATO-Angehörige von Multimedia über Kosmetik und Vitaminprodukte bis hin zum Heimtierbedarf versorgen. Ebenfalls ist hier das „Sport Recreations Center“ vorzufinden, eine Art Freizeitorganisation für die US-Truppen, die ebenfalls das örtliche [[Paintball]]feld leitet.\n" +
                "\n" +
                "=== Bildungseinrichtungen ===\n" +
                "Die zahlreichen Bildungs- und Kultureinrichtungen Böblingens bieten ein differenziertes Angebot. Weiterführende und berufliche Schulen mit einem weiten Einzugsgebiet bilden im Verbund mit der Stadtbibliothek, der Musik- und Kunstschule und der Volkshochschule ein breites Spektrum zur Aus- und Weiterbildung sowie zur Freizeitgestaltung direkt vor Ort.\n" +
                "\n" +
                "In Böblingen gibt es vier allgemeinbildende [[Gymnasium|Gymnasien]] ([[Albert-Einstein-Gymnasium (Böblingen)|Albert-Einstein-Gymnasium]], [[Lise-Meitner-Gymnasium (Böblingen)|Lise-Meitner-Gymnasium]], [[Max-Planck-Gymnasium (Böblingen)|Max-Planck-Gymnasium]] und [[Otto-Hahn-Gymnasium (Böblingen)|Otto-Hahn-Gymnasium)]], zwei [[Realschule]]n (Albert-Schweitzer- und Friedrich-Schiller-Realschule), eine [[Förderschule (Deutschland)|Förderschule]] (Pestalozzischule), eine Grund- und [[Werkrealschule]] (Eichendorff-GWRS), eine Werkrealschule (Theodor-Heuss-WRS), sechs reine [[Grundschule]]n in der Kernstadt (Eduard-Mörike-, Erich-Kästner-, Friedrich-Silcher-, Justinus-Kerner-, Ludwig-Uhland- und Wilhelm-Hauff-Schule) sowie eine Grundschule im Stadtteil Dagersheim.\n" +
                "\n" +
                "Der [[Landkreis Böblingen]] ist Schulträger der drei [[Berufsbildende Schule|Beruflichen Schulen]] ([[Akademie für Datenverarbeitung Böblingen|Akademie für Datenverarbeitung Böblingen – ADV]]; Kaufmännisches Schulzentrum Böblingen – KSZ-BB, unter anderem mit [[Wirtschaftsgymnasium]]; Mildred-Scheel-Schule Böblingen, unter anderem mit Biotechnologischem Gymnasium und Ernährungswissenschaftlichem Gymnasium). Des Weiteren ist hier die Käthe-Kollwitz-Schule für Geistigbehinderte mit Schulkindergarten für Geistigbehinderte zu erwähnen.\n" +
                "\n" +
                "Die Prisma Privatschulen (Realschule-Gymnasium), die private Altenpflegeschule der Arbeiterwohlfahrt Nordwürttemberg e.&nbsp;V., die Freie Evangelische Schule Böblingen e.&nbsp;V. (Grund- und Hauptschule), die Freie [[Waldorfschule]] Böblingen/Sindelfingen e.&nbsp;V. und die Private Berufsfachschule im Bildungszentrum Böblingen des Internationalen Bundes e.&nbsp;V. runden das schulische Angebot in Böblingen ab.\n" +
                "\n" +
                "Erwachsenenbildung bietet die vhs.Böblingen-Sindelfingen e.&nbsp;V. und der Verein zur Förderung der Berufsbildung e. V., eine Bildungseinrichtung der [[Industrie- und Handelskammer|IHK]], an.\n" +
                "\n" +
                "=== Restmüllheizkraftwerk ===\n" +
                "Östlich von Böblingen befindet sich in einem 40 Meter hohen Gebäude ein Restmüllheizkraftwerk mit einer Erzeugerleistung von 12&nbsp;MW. Der Kamin des Restmüllheizkraftwerks hat eine Höhe von 55 Metern. Die Netto-Wärmeleistung der mit 2 Linien ausgestatteten Anlage, die pro Stunde 9,43 Tonnen Restmüll verfeuern kann, beträgt 48,4&nbsp;MW.\n" +
                "\n" +
                "== Kultur und Sehenswürdigkeiten ==\n" +
                "=== Museen und Galerien ===\n" +
                "* Die 1593 erbaute [[Zehntscheuer]] beherbergt heute die [[Städtische Galerie Böblingen]] und das [[Deutsches Bauernkriegsmuseum Böblingen|Bauernkriegsmuseum]]. Das Bauernkriegsmuseum dokumentiert auch die Schlacht von Böblingen von 1525 und enthält ein Zinnfiguren[[diorama]].\n" +
                "* Die galerie contact ist ein städtisch betreuter Ausstellungsort für regionale Künstler und Böblinger Kunstvereine sowie kulturell aktive Gruppierungen aus Böblingen.\n" +
                "* Im Gebäude des Alten Amtsgerichts, erbaut 1833 – heute Sitz u.&nbsp;a. des Böblinger Kunstvereins –, befindet sich die [[Schleuse 16]], ein Ausstellungsraum für bevorzugt experimentelle Kunst.\n" +
                "* Das [[Deutsches Fleischermuseum|Deutsche Fleischermuseum]] ist in einem Fachwerkhaus (mit Wandmalereien im Inneren) im Stadtzentrum beheimatet.\n" +
                "* Das [[Heimatmuseum des Nordböhmischen Niederlandes]] ist im Deutschen Fleischermuseum Böblingen untergebracht. Im Mittelpunkt der Sammlung stehen Landschafts- und Hausmodelle, Arbeitsgerätschaften, Trachten und Erinnerungsstücke aus dem Nordböhmischen Niederland.\n" +
                "\n" +
                "=== Bauwerke ===\n" +
                "[[Datei:Stadtkirche Oberer See Böblingen.jpg|mini|Oberer See und Stadtkirche]]\n" +
                "* Die Evangelische ''Stadtpfarrkirche St. Dionysius'' am Marktplatz ist das [[Wahrzeichen]] der Stadt. Der Turm der Kirche entstand wohl bereits zur Zeit der Stadtgründung, das Langhaus im 14. Jahrhundert. Die Kirche wurde im [[Zweiter Weltkrieg|Zweiten Weltkrieg]] zerstört und danach wieder aufgebaut.\n" +
                "* Das ehemalige Schloss wurde nach der Zerstörung im Krieg nicht wieder aufgebaut.\n" +
                "* Die Evangelische ''Agathenkirche'' in Böblingen-Dagersheim ist ähnlich alt wie die Stadtkirche, Schiff und Chor wurden 1491 in ihrer heutigen Gestalt erneuert. Das Kirchenschiff fasst 500 Menschen. Das in Holz gearbeitete Chorgestühl zeigt wertvolle Schnitzarbeiten des 15. Jahrhunderts. Der 36 Meter hohe Wehrturm ist weithin sichtbar, die historische Kegelladenorgel von [[Carl Gottlieb Weigle]] aus dem Jahre 1857 ist die älteste spielbare Orgel der ganzen Region.\n" +
                "* Weitere größere Kirchen in Böblingen sind die 1960/61 erbaute Paul-Gerhardt-Kirche für die evangelische Kirchengemeinde in der Weststadt und die 1959/60 erbaute Martin-Luther-Kirche für die evangelischen Gläubigen der Oststadt.\n" +
                "* [[Hans Scharoun]] schuf 1963–1966 eine aus sieben Häusern bestehende Wohnsiedlung auf dem ''Rauhen Kapf'', 1971 sein ''Orplid'', eines seiner letzten Hochhäuser mit bemerkenswerter Architektur, im Westen der Stadt.\n" +
                "* Der 82&nbsp;Meter hohe Kamin des 1978 errichteten [[Fernheizwerk]]s in der Ernst-Reuter-Straße ist das höchste Bauwerk im Stadtgebiet.\n" +
                "* Der 31&nbsp;Meter hohe [[Wasserturm Böblingen|Wasserturm auf der Waldburg]] wurde 1928<ref>[http://structurae.de/bauwerke/wasserturm-auf-der-waldburg ''Wasserturm auf der Waldburg''.] In: [[Structurae]].</ref> errichtet und hat eine 28&nbsp;Meter<ref>Nachrichten Kreis-BB, Lokales: [http://www.szbz.de/nachrichten/news-detail-kreis-bb/ueber-151-stufen-zum-weitblick-764219.html ''Über 151 Stufen zum Weitblick''], szbz.de, 11.&nbsp;Dezember 2012, abgerufen am 28.&nbsp;November 2014. (Angabe der Gesamthöhe in anderen Quellen 31&nbsp;m)</ref> hohe Aussichtsplattform.\n" +
                "\n" +
                "[[Datei:Schornstein Heizwerk Boeblingen.JPG|mini|hochkant|Kamin des Fernheizwerks in der Ernst-Reuter-Straße]]\n" +
                "\n" +
                "=== Die Seen ===\n" +
                "[[Datei:Seehbuch 04r Böblingen.jpg|mini|Ansicht der Seen aus dem württembergischen ''Seehbuch'', Anfang 17. Jahrhundert]]\n" +
                "Im Zuge der [[Landesgartenschau]] 1996 wurde der Bereich um den „Oberen“ und den „Unteren“ See großzügig neu gestaltet.\n" +
                "Im Gebiet [[Flugfeld (Stadtteil)|Flugfeld]] wurde der „Lange See“ geschaffen und 2010 geflutet.\n" +
                "\n" +
                "=== Regelmäßige Veranstaltungen ===\n" +
                "* Stadtfest („Böblinger Jahrmarkt“) im Juli, das rund um den Schlossberg stattfindet.\n" +
                "* Seit 1996 findet jährlich von Juni bis September der „Böblinger Sommer am See“ mit über 60 Einzelveranstaltungen (z.&nbsp;B. Konzerte, Flohmarkt, Schlemmen am See etc.) rund um die neugestalteten Seen statt.\n" +
                "* Regelmäßig erfolgten Konzerte und Veranstaltungen in der mittlerweile abgerissenen [[Sporthalle Böblingen|Sporthalle]] und in der [[Kongresshalle Böblingen|Kongresshalle]] ([[Wetten, dass..?]], [[Verstehen Sie Spaß?]]).\n" +
                "* Umzug am Rosenmontag: Jährlich am Rosenmontag, veranstaltet von Grün-Weiß Böblingen e.&nbsp;V. Lokale Gruppen ziehen mit vielen auswärtigen „Hästrägern“ und Musikgruppen durch die Stadt.\n" +
                "* Das Fischsuppenessen findet am Aschermittwoch statt. Der Gewinn dieser Veranstaltung wird zugunsten von Multiple-Sklerose-Kranken und anderer humanitärer Organisationen verwendet.\n" +
                "* Das „Böblinger Open“, ein offenes Schachturnier, lockt alljährlich vom 26. bis 30. Dezember über 300 Schachspieler aus ganz Europa nach Böblingen.\n" +
                "\n" +
                "=== Mineraltherme Böblingen ===\n" +
                "Eine in 775&nbsp;m Tiefe erschlossene salzhaltige Mineralthermalquelle speist die Mineraltherme Böblingen, die Entspannungs-, Wellness- und Gesundheitsprogramme anbietet.<ref>http://www.mineraltherme-boeblingen.de/</ref>\n" +
                "\n" +
                "Das Thermalwasser fließt in drei Innen- und zwei Außenbecken mit Temperaturen von 31&nbsp;°C bis 36&nbsp;°C, in einen Whirlpool und Sprudelliegen. Außerdem gibt es einen 1400&nbsp;m² großen, bepflanzten Saunagarten mit mehreren Saunen und Dampfbädern. Die Mineraltherme wurde 1989 eröffnet.<ref>[http://www.boeblingen.de/,Lde/10424186.html ''25 Jahre Mineraltherme Böblingen'']</ref> Zuvor befanden sich an dieser Stelle KFZ-Werkstätten und Autohäuser.\n" +
                "\n" +
                "== Persönlichkeiten ==\n" +
                "=== Ehrenbürger ===\n" +
                "Die Stadt Böblingen oder die frühere Gemeinde Dagersheim haben folgenden Personen das [[Ehrenbürger]]recht verliehen:\n" +
                "* 1850: [[Immanuel Gottlieb Kolb]] (1784–1859), Pietist, Schulmeister in Dagersheim\n" +
                "* 1874: [[Otto Elben]] (1823–1899), wegen der Durchsetzung der über Böblingen verlaufenden Trasse der Gäubahn; Politiker, Journalist, Verleger Schwäbischer Merkur, Gründer des [[Schwäbischer Sängerbund|schwäbischen Sängerbundes]]\n" +
                "* 1913: Lyon Sussmann (1843–1935), Unternehmer (Hautana), Wohltäter der Stadt, stiftete z.&nbsp;B. den ersten städtischen Kindergarten in der Lange Straße.\n" +
                "* 1933: [[Adolf Hitler]], [[Hermann Göring]] und [[Max Luib]] (die drei Ehrenbürgerschaften wurden 1946 aberkannt)\n" +
                "* 1933: [[Wilhelm Murr]] (1888–1945), württembergischer Staatspräsident und Reichsstatthalter (die Ehrenbürgerschaft wurde 2011 aberkannt)\n" +
                "* 1955: [[Hanns Klemm]] (1885–1961), Gründer der Klemm-Leichtflugzeugwerke in Böblingen, Pionier der Deutschen Sportfliegerei\n" +
                "* 1963: Adolf Reisser (1897–1977), erhielt das Ehrenbürgerrecht aus Anlass seines 50-jährigen Arbeitsjubiläums, beispielhafter Unternehmer für den Wiederaufbau der Wirtschaft nach dem Krieg\n" +
                "* 1967 (Dagersheim): Schwester Marie Ziegler (1892–1979)\n" +
                "* 1986: Wolfgang Brumme (<!--25. August -->1920–<!--20. Oktober -->1999), Oberbürgermeister der Stadt Böblingen von 1948 bis 1986\n" +
                "* 2010: [[Alexander Vogelgsang]], Oberbürgermeister der Stadt Böblingen von 1986 bis 2010\n" +
                "\n" +
                "=== Söhne und Töchter der Stadt ===\n" +
                "* [[Carl Friedrich Gerstlacher]] (1732–1795), württembergischer und badischer Hofjurist\n" +
                "* [[Jakob Christian Schlotterbeck]] (1757–1811), Porträtmaler und Kupferstecher\n" +
                "* [[Wilhelm Ganzhorn]] (1818–1880), Jurist und Amtsrichter, Dichter des Volksliedes: „Im schönsten Wiesengrunde“\n" +
                "* [[Albert von Häberlen]] (1843–1921), Regierungspräsident des Neckarkreises und des Jagstkreises \n" +
                "* [[Julius Hartranft]] (1844–1906), Pädagoge, Landtagsabgeordneter\n" +
                "* [[Alfred Hartranft]] (1847–1930), Stadtschultheiß in Freudenstadt, Landtagsabgeordneter\n" +
                "* Ernst Gottlieb Theodor Bonz, (1832 - 1898) Chemiker und Produzent des ersten absolut reinen Äthers für Narkosen\n" +
                "* [[Paul Lechler]] (1849–1925), Unternehmer und Gründer des Deutschen Instituts für Ärztliche Mission\n" +
                "* [[Paul Dinkelacker]] (1873–1958), Unternehmer\n" +
                "* [[Theodor Niethammer]] (1876–1947), Schweizer Astronom und Geodät, Ordinarius an der Universität Basel\n" +
                "* [[Alfred Löckle]] (1878–1943), Bibliothekar\n" +
                "* [[Karl Berner (Mediziner)|Karl Berner]] (1888–1971), Mediziner\n" +
                "* [[Rudolf Christian Baisch]] (1903–1990), Bildhauer, Lyriker und Maler\n" +
                "* [[Werner Meyer-König]] (1912–2002), Mathematiker\n" +
                "* [[Erwin Lamparter]] (1923–1998), Bürgermeister von Maichingen, Landtagsabgeordneter\n" +
                "* [[Peter Cramer (Jurist)|Peter Cramer]] (1932–2009), Rechtswissenschaftler\n" +
                "* [[Manfred Beilharz]] (* 1938), Dramaturg und Intendant\n" +
                "* [[Eugen Klunzinger]] (* 1938), Jurist und Politiker, Landtagsabgeordneter 1979 bis 2006\n" +
                "* [[Helmut Baur (Unternehmer)|Helmut Baur]] (* 1941), Unternehmer\n" +
                "* [[Hermann Schwander]] (* 1948), Musiker und Hochschullehrer\n" +
                "* [[Regine Stachelhaus]] (* 1955), Juristin\n" +
                "* [[Wolfgang Marquardt]] (* 1956), Professor für Prozesstechnik an der RWTH Aachen\n" +
                "* [[Ewald Langer]] (* 1960), Biologe und Hochschulprofessor\n" +
                "* [[Gerhard Strittmatter]] (* 1961), Bahnradsportler und Weltmeister\n" +
                "* [[Paul Nemeth]] (* 1965), Politiker (CDU), seit 2006 Landtagsabgeordneter\n" +
                "* [[Wolfgang Bauer (Trompeter)|Wolfgang Bauer]] (* 1968), Trompeter\n" +
                "* [[Alkuin Volker Schachenmayr]] (* 1969 als Volker Schachenmayr), Priestermönch, Professor des Zisterzienserstiftes Heiligenkreuz\n" +
                "* [[Birgit Wolf]] (* 1969), Leichtathletin und Olympiateilnehmerin\n" +
                "* [[Marko Schacher]] (* 1970), Journalist, Kurator und Autor\n" +
                "* [[Sven Scheuer]] (* 1971), Fußballspieler, Deutscher Fußballmeister mit dem FC Bayern München\n" +
                "* [[Oliver Ziegenbalg]] (* 1971), Drehbuchautor\n" +
                "* [[Timo Novotny]] (* 1973), Filmemacher und Fotograf\n" +
                "* [[Jörg Enz]] (* 1974), Jazzmusiker\n" +
                "* [[Frank Kowatschitsch]] (* 1974), Radrennfahrer\n" +
                "* [[Meike Entenmann]] (* 1975), Bildhauerin, Malerin und Grafikerin\n" +
                "* [[Alexander Kleider]] (* 1975), Dokumentarfilmer\n" +
                "* [[Sebastian Hess (Sportwissenschaftler)|Sebastian Hess]] (* 1977), Sportwissenschaftler, Leichtathletiktrainer\n" +
                "* [[Holger Wohland]] (* 1977), Fußballspieler\n" +
                "* [[René Weissinger]] (* 1978), Radrennfahrer\n" +
                "* [[Bastian Braig]] (* 1979), Schauspieler und Produzent\n" +
                "* [[Barbara Bürkle]] (* 1979), Jazzsängerin\n" +
                "* [[Judith Goldbach]] (* 1981), Jazzmusikerin\n" +
                "* [[Kathrin Volz]] (* 1982), Duathletin und Triathletin\n" +
                "* [[Marcel Wagner]] (* 1982), Hörfunk- und Fernsehmoderator\n" +
                "* [[Vincenzo Marchese]] (* 1983), Fußballspieler\n" +
                "* [[Tobias Becker (Jazzmusiker)|Tobias Becker]] (* 1984), Jazzmusiker und Komponist\n" +
                "* [[Stefan Jarosch]] (* 1984), Fußballspieler, mehrfacher Jugendnationalspieler\n" +
                "* [[Lena Knauss]] (* 1984), Regisseurin und Drehbuchautorin\n" +
                "* [[Damon Paul]] (* 1985), DJ und Musikproduzent\n" +
                "* [[Manuel Salz]] (* 1985), Fußballspieler\n" +
                "* [[Lukas Kiefer]] (* 1993), Fußballspieler\n" +
                "* [[Tim Kübel]] (* 1993), Fußballspieler\n" +
                "* [[Fabienne Dongus]] (* 1994), Fußballspielerin\n" +
                "* [[Tamar Dongus]] (* 1994), Fußballspielerin\n" +
                "* [[Jasmin Ballach]] (* 1996), Fußballspielerin\n" +
                "* [[Bianca Blöchl]] (* 1996), Fußballspielerin\n" +
                "* [[Timo Baumgartl]] (* 1996), Fußballspieler\n" +
                "* [[Marc Jurczyk]] (* 1996), Radsportler\n" +
                "* [[Simon Kranitz]] (* 1996), Fußballspieler\n" +
                "\n" +
                "=== Weitere mit Böblingen verbundene Persönlichkeiten ===\n" +
                "* [[Immanuel Gottlieb Kolb]] (1781–1859), pietistischer Pädagoge\n" +
                "* [[Botho Henning Elster]] (1894–1952), Generalmajor im Zweiten Weltkrieg; lebte und starb in Böblingen\n" +
                "* [[Robert Maresch]] (1903–1989), Politiker; lebte in Böblingen\n" +
                "* [[Erwin Aikele]] (1904–2002), Informatiker, Kommunalpolitiker\n" +
                "* [[Manfred H. Pilkuhn]] (1934–2015), Physiker; lebte in Böblingen\n" +
                "\n" +
                "== Literatur ==\n" +
                "* [[Erich Keyser]] (Hrsg.): ''Württembergisches Städtebuch.'' Band IV: Teilband Baden-Württemberg, Band 2 aus ''Deutsches Städtebuch. Handbuch städtischer Geschichte'' – Im Auftrage der Arbeitsgemeinschaft der historischen Kommissionen und mit Unterstützung des Deutschen Städtetages, des Deutschen Städtebundes und des Deutschen Gemeindetages, Stuttgart 1961.\n" +
                "* Andreas Wiedenmann und die Evangelische Kirchengemeinde Dagersheim (Herausgebergem.): ''Kleiner Dagersheimer Kirchenführer. Festschrift zum fünfhundertjährigen Jubiläum der Dagersheimer Kirche'', Böblingen 1991.\n" +
                "* [[Sönke Lorenz]], Günter Scholz (Hrsg.): ''Böblingen. Vom Mammutzahn zum Mikrochip''. Filderstadt 2003, ISBN 3-935129-09-2.\n" +
                "* Julius Fekete: ''Kunst- und Kulturdenkmale im Landkreis Böblingen''. Mit Fotos von Joachim Feist. Stuttgart 2006, ISBN 978-3-8062-1969-2.\n" +
                "\n" +
                "== Weblinks ==\n" +
                "{{Commonscat|Böblingen|Böblingen}}\n" +
                "{{Wikisource|Topographia Sueviae: Beblingen|Böblingen in der Topographia Sueviae (Mathäus Merian) von 1656}}\n" +
                "{{Wikisource|Beschreibung des Oberamts Böblingen/Kapitel B 1|Böblingen in der Beschreibung des Oberamts von 1850}}\n" +
                "* [http://www.boeblingen.de/ Website der Stadt Böblingen]\n" +
                "\n" +
                "== Einzelnachweise ==\n" +
                "<references />\n" +
                "\n" +
                "{{Navigationsleiste Städte und Gemeinden im Landkreis Böblingen}}\n" +
                "\n" +
                "{{Normdaten|TYP=g|GND=4007430-4|VIAF=247613498}}\n" +
                "\n" +
                "{{SORTIERUNG:Boblingen}}\n" +
                "[[Kategorie:Ort im Landkreis Böblingen]]\n" +
                "[[Kategorie:Böblingen| ]]\n" +
                "[[Kategorie:Kreisstadt in Baden-Württemberg]]\n" +
                "[[Kategorie:Große Kreisstadt in Baden-Württemberg]]\n";


        Long duration = 0L;
        WikiConfig config = DefaultConfigEnWp.generate();
        WtEngineImpl engine = new WtEngineImpl(config);
        String html = null;
        for (int i = 0; i < 100; i++) {
            Long start = System.currentTimeMillis();
            PageTitle pageTitle = PageTitle.make(config, "Test");
            PageId pageId = new PageId(pageTitle, -1);
            EngProcessedPage cp = engine.postprocess(pageId, raw, null);

            html = HtmlRenderer.print(
                    new BlackoutPediaHtmlRendererCallback(""),
                    config,
                    pageTitle,
                    cp.getPage()
            );
            duration += System.currentTimeMillis() - start;
        }
        System.out.println("avg runtime sweble: " + duration / 100);

        assertTrue(html.contains("Grillplatz befindet.<a rel=\"nofollow\" class=\"external text\" href=\"http://www.szbz.de/nachrichten/artikel/detail/weite-saetze-und-harte-landungen-9-2-2013/\">[1]</a>"));
    }
}
