<map version="0.8.0">
<!-- To view this file, download free mind mapping software FreeMind from http://freemind.sourceforge.net -->
<node CREATED="1164144050609" ID="Freemind_Link_1281552056" MODIFIED="1164144059109" TEXT="BonzaiGotchi">
<node CREATED="1164146247531" HGAP="94" ID="Freemind_Link_1862827424" MODIFIED="1164361749258" POSITION="right" TEXT="Element" VSHIFT="33">
<node CREATED="1164200885787" ID="Freemind_Link_585845322" MODIFIED="1164297996592" TEXT="Variabeln">
<node CREATED="1164200912145" ID="Freemind_Link_1705435457" MODIFIED="1164201275409" TEXT="lenght [short]">
<node CREATED="1164304152904" ID="Freemind_Link_817765553" MODIFIED="1164309789703" TEXT="1000 = 1 pixel"/>
</node>
<node CREATED="1164200954470" FOLDED="true" ID="Freemind_Link_992290852" MODIFIED="1164365772885" TEXT="thickness [byte]">
<node CREATED="1164304152904" ID="Freemind_Link_747667691" MODIFIED="1164309789703" TEXT="1000 = 1 pixel"/>
</node>
<node CREATED="1164201276503" ID="Freemind_Link_1657294251" MODIFIED="1164201290237" TEXT="angle [short]"/>
<node CREATED="1164201295644" ID="Freemind_Link_1980897300" MODIFIED="1164201305362" TEXT="posX [short]"/>
<node CREATED="1164201306628" ID="Freemind_Link_938933602" MODIFIED="1164201315425" TEXT="posY [short]"/>
<node CREATED="1164201853680" FOLDED="true" ID="Freemind_Link_1673378872" MODIFIED="1164297502305" TEXT="water [int]">
<node CREATED="1164297427567" ID="Freemind_Link_1959117720" MODIFIED="1164297450615" TEXT="0 - demand*100"/>
</node>
<node CREATED="1164201875837" FOLDED="true" ID="Freemind_Link_966520874" MODIFIED="1164297403455" TEXT="health [short]">
<node CREATED="1164297420379" ID="Freemind_Link_857634127" MODIFIED="1164297425598" TEXT="0 - 100"/>
</node>
<node CREATED="1164296998524" ID="Freemind_Link_1649172789" MODIFIED="1164297525947" TEXT="demand [short]">
<node CREATED="1164297526525" ID="Freemind_Link_841823949" MODIFIED="1164297531760" TEXT="lenght * thick"/>
</node>
<node CREATED="1164302717653" ID="Freemind_Link_1440947657" MODIFIED="1164302741199" TEXT="waterRequest [short]"/>
<node CREATED="1164203372407" ID="Freemind_Link_1548541333" MODIFIED="1164207847537" TEXT="childWaterRequest [short]"/>
<node CREATED="1164301781797" FOLDED="true" ID="Freemind_Link_663735406" MODIFIED="1164301882152" TEXT="childWaterDivider [byte]">
<node CREATED="1164301883339" ID="Freemind_Link_1917068697" MODIFIED="1164301917916" TEXT="1-100"/>
</node>
<node CREATED="1164201423126" ID="Freemind_Link_959741097" MODIFIED="1164361728258" TEXT="childLeft [Element]" VSHIFT="4">
<arrowlink DESTINATION="Freemind_Link_1862827424" ENDARROW="Default" ENDINCLINATION="35;-11;" ID="Freemind_Arrow_Link_1830305447" STARTARROW="None" STARTINCLINATION="-68;-14;"/>
</node>
<node CREATED="1164201456439" ID="Freemind_Link_1611863647" MODIFIED="1164361735539" TEXT="childCenter [Element]">
<arrowlink DESTINATION="Freemind_Link_1862827424" ENDARROW="Default" ENDINCLINATION="50;-7;" ID="Freemind_Arrow_Link_624006312" STARTARROW="None" STARTINCLINATION="-87;-19;"/>
</node>
<node CREATED="1164201462924" ID="Freemind_Link_305680291" MODIFIED="1164361749258" TEXT="childRight [Element]">
<arrowlink DESTINATION="Freemind_Link_1862827424" ENDARROW="Default" ENDINCLINATION="71;56;" ID="Freemind_Arrow_Link_1965287546" STARTARROW="None" STARTINCLINATION="-93;-23;"/>
</node>
</node>
<node CREATED="1164200895193" ID="Freemind_Link_666563809" MODIFIED="1164200901505" TEXT="Funktionen">
<node CREATED="1164144068281" FOLDED="true" ID="_" MODIFIED="1164147517062" TEXT="draw(g)">
<node CREATED="1164144073796" ID="Freemind_Link_1416321483" MODIFIED="1164309847968" TEXT="if (dicke /1000  % 2 == 0)">
<node CREATED="1164145939250" ID="Freemind_Link_513700641" MODIFIED="1164145953359" TEXT="true: 1 pixel versatz"/>
<node CREATED="1164145956046" ID="Freemind_Link_1379482431" MODIFIED="1164145972312" TEXT="false: zentriert"/>
</node>
<node CREATED="1164145982484" ID="Freemind_Link_1918046971" MODIFIED="1164194193032" TEXT="berechnung linienende">
<node CREATED="1164146013515" ID="Freemind_Link_544685559" MODIFIED="1164146085859" TEXT="x2 = cos(winkel) * l&#xe4;nge"/>
<node CREATED="1164146036218" ID="Freemind_Link_1151587101" MODIFIED="1164146089609" TEXT="y2 = sin(winkel) * l&#xe4;nge"/>
</node>
<node CREATED="1164146092781" ID="Freemind_Link_765170539" MODIFIED="1164146105078" TEXT="loop dicke">
<node CREATED="1164146108765" ID="Freemind_Link_1964077508" MODIFIED="1164146207281" TEXT="if (winkel &gt;= 45 &amp;&amp; &lt;= 135)">
<node CREATED="1164146208500" ID="Freemind_Link_837356881" MODIFIED="1164146226484" TEXT="true: horizontaler versatz der linien"/>
<node CREATED="1164146229031" ID="Freemind_Link_89513176" MODIFIED="1164146237937" TEXT="false: vertikaler versatz der linien"/>
</node>
<node CREATED="1164309877796" ID="Freemind_Link_460899127" MODIFIED="1164309888640" TEXT="je dicke eine linie zeichnen"/>
</node>
<node CREATED="1164303705262" ID="Freemind_Link_1378000501" MODIFIED="1164305006173" TEXT="children.draw(g)"/>
</node>
<node CREATED="1164146342703" ID="Freemind_Link_1248054151" MODIFIED="1164298542260" TEXT="[short] grow(supply [short])">
<node CREATED="1164300795466" ID="Freemind_Link_1054748840" MODIFIED="1164300801904" TEXT="verbrauch">
<node CREATED="1164296892240" ID="Freemind_Link_1321932584" MODIFIED="1164299020192" TEXT="if ((childWaterRequest/2)&gt; supply) &amp;&amp; (water &gt; 75%)">
<node CREATED="1164299021755" ID="Freemind_Link_1947104335" MODIFIED="1164303196919" TEXT="true: supplyTaken [short]=min(request/2,supply)"/>
<node CREATED="1164299021755" ID="Freemind_Link_77077293" MODIFIED="1164303205715" TEXT="false: supplyTaken [short]=min(request,supply)"/>
</node>
<node CREATED="1164299459872" ID="Freemind_Link_308171245" MODIFIED="1164299485044" TEXT="supply, demand -= supplyTaken"/>
<node CREATED="1164299621281" ID="Freemind_Link_1029075407" MODIFIED="1164299628531" TEXT="water -= demand;"/>
</node>
<node CREATED="1164300402050" ID="Freemind_Link_178339646" MODIFIED="1164300404691" TEXT="health">
<node CREATED="1164207878784" ID="Freemind_Link_201052276" MODIFIED="1164298410695" TEXT="If(water&lt;30%) health--;">
<node CREATED="1164290764489" ID="Freemind_Link_1681403963" MODIFIED="1164290788603" TEXT="&lt;10 -10; &lt;30 -5"/>
</node>
<node CREATED="1164290638180" ID="Freemind_Link_201913587" MODIFIED="1164298405304" TEXT="If(water &gt;50 &lt;90 health++)">
<node CREATED="1164290808836" ID="Freemind_Link_665807177" MODIFIED="1164300444114" TEXT="30-49 +2; 50-59 +3; 60-85 +2; &gt;90 -3"/>
</node>
</node>
<node CREATED="1164300820248" ID="Freemind_Link_446286864" MODIFIED="1164300826827" TEXT="wachsen">
<node CREATED="1164303955035" ID="Freemind_Link_284690920" MODIFIED="1164303966331" TEXT="if (children) length stop"/>
<node CREATED="1164303985737" ID="Freemind_Link_1411384785" MODIFIED="1164304334710" TEXT="else length:dicke == 25:1"/>
<node CREATED="1164304571000" ID="Freemind_Link_706119740" MODIFIED="1164304676402" TEXT="if (water &gt; 40 % &amp;&amp; health &gt; 70)">
<node CREATED="1164304685824" ID="Freemind_Link_1614161006" MODIFIED="1165411388289" TEXT="length+=33; dicke += 2"/>
</node>
</node>
<node CREATED="1164301066161" ID="Freemind_Link_1812593820" MODIFIED="1164301964523" TEXT="childWaterRequest">
<node CREATED="1164301117818" ID="Freemind_Link_586222244" MODIFIED="1164301125475" TEXT="demand = thick * length"/>
<node CREATED="1164302036021" ID="Freemind_Link_1637434327" MODIFIED="1164302422100" TEXT="= 0"/>
<node CREATED="1164301357481" ID="Freemind_Link_462216251" MODIFIED="1164302263309" TEXT="children">
<node CREATED="1164302090050" ID="Freemind_Link_749447979" MODIFIED="1164302193468" TEXT="1. child">
<node CREATED="1164302195077" ID="Freemind_Link_1181008819" MODIFIED="1164302211123" TEXT="tmpSupply = (supply * childWaterDivider / 100)"/>
<node CREATED="1164302226763" ID="Freemind_Link_987490818" MODIFIED="1164303497034" TEXT="tmpChildWaterRequest = child1.grow(tmpSupply)"/>
<node CREATED="1164302293026" ID="Freemind_Link_1593820842" MODIFIED="1164302300636" TEXT="supply -= tmpSupply"/>
<node CREATED="1164302362836" ID="Freemind_Link_30509268" MODIFIED="1164302375711" TEXT="childWaterRequest += tmpChildWaterRequest"/>
</node>
<node CREATED="1164302302448" ID="Freemind_Link_1439559196" MODIFIED="1164302307370" TEXT="2. child">
<node CREATED="1164302319666" ID="Freemind_Link_947077730" MODIFIED="1164303538767" TEXT="childWaterRequest += child2.grow(supply)"/>
</node>
</node>
<node CREATED="1164302098003" ID="Freemind_Link_1202169345" MODIFIED="1164302130533" TEXT="childWaterDivider = tmpChildWaterRequest / childWaterRequest * 100"/>
<node CREATED="1164302397882" ID="Freemind_Link_716713347" MODIFIED="1164302785572" TEXT="+= waterRequest = ">
<node CREATED="1164296911585" ID="Freemind_Link_1120518122" MODIFIED="1164297089525" TEXT="if (water &lt; 25%) =&gt; 3 * demand"/>
<node CREATED="1164297091432" ID="Freemind_Link_401393334" MODIFIED="1164297135199" TEXT="if (water &lt; 50%) =&gt; 2 * demand"/>
<node CREATED="1164297136558" ID="Freemind_Link_143315410" MODIFIED="1164297154294" TEXT="if (water &lt; 75 %) =&gt; 1,5*demand"/>
<node CREATED="1164297155294" ID="Freemind_Link_899630775" MODIFIED="1164297169702" TEXT="if (water &lt; 95%) =&gt; 1,1 * demand"/>
<node CREATED="1164297171639" ID="Freemind_Link_1220885097" MODIFIED="1164297194329" TEXT="if (water &gt;= 95%) =&gt; 1,0 * demand"/>
</node>
</node>
<node CREATED="1164207931078" ID="Freemind_Link_579855158" MODIFIED="1164302816649" TEXT="return childWaterRequest;"/>
</node>
<node CREATED="1164147228984" ID="Freemind_Link_1246844344" MODIFIED="1164147235093" TEXT="split()"/>
<node CREATED="1164203678649" ID="Freemind_Link_272841071" MODIFIED="1164207795781" TEXT="([short] getChildcount)">
<node CREATED="1164205227220" HGAP="22" ID="Freemind_Link_822713426" MODIFIED="1164205236892" TEXT="childCount = 0;" VSHIFT="6"/>
<node CREATED="1164205206782" ID="Freemind_Link_827641801" MODIFIED="1164205255580" TEXT="childCount += childX.getChildcount" VSHIFT="-4"/>
<node CREATED="1164205260158" ID="Freemind_Link_917803736" MODIFIED="1164205265174" TEXT="childCount++"/>
</node>
</node>
<node CREATED="1164294281563" ID="Freemind_Link_513337268" MODIFIED="1164312861046" TEXT="Wasser Kapazit&#xe4;t">
<node CREATED="1164294289392" ID="Freemind_Link_872881045" MODIFIED="1164294300159" TEXT="Verbrauch * 100"/>
</node>
</node>
<node CREATED="1164147113781" ID="Freemind_Link_812857274" MODIFIED="1164147116421" POSITION="left" TEXT="Navigation">
<node CREATED="1164147117015" ID="Freemind_Link_1489868156" MODIFIED="1164147128625" TEXT="Einblenden von m&#xf6;glichen Richtungen"/>
<node CREATED="1164147198453" ID="Freemind_Link_1024173999" MODIFIED="1164147219296" TEXT="Ausgew&#xe4;hltes Objekt besitzt Listener"/>
</node>
<node CREATED="1164147557156" ID="Freemind_Link_1511887774" MODIFIED="1164202090562" POSITION="right" TEXT="GlobalVars">
<node CREATED="1164147567578" ID="Freemind_Link_1075165033" MODIFIED="1164147575046" TEXT="Statische Variablen">
<node CREATED="1164147576765" ID="Freemind_Link_608462307" MODIFIED="1164147729031" TEXT="Wachstumsinterval (S)"/>
<node CREATED="1164147604937" ID="Freemind_Link_93798441" MODIFIED="1164147778046" TEXT="Wachstumsrate (E)"/>
<node CREATED="1164194981045" ID="Freemind_Link_361269841" MODIFIED="1164195011544" TEXT="Matrize Wachstum (S)">
<node CREATED="1164195101822" ID="Freemind_Link_1595508142" MODIFIED="1164195104244" TEXT="X">
<node CREATED="1164195020028" ID="Freemind_Link_1667307354" MODIFIED="1164195022012" TEXT="ohneKind"/>
<node CREATED="1164195011544" ID="Freemind_Link_307893527" MODIFIED="1164195065136" TEXT="einKind"/>
<node CREATED="1164195051621" ID="Freemind_Link_1562782986" MODIFIED="1164195053605" TEXT="zweiKind"/>
</node>
<node CREATED="1164195111916" ID="Freemind_Link_222431706" MODIFIED="1164195113306" TEXT="Y">
<node CREATED="1164195114228" ID="Freemind_Link_1771397836" MODIFIED="1164195119009" TEXT="L&#xe4;nge"/>
<node CREATED="1164195119728" ID="Freemind_Link_1421383899" MODIFIED="1164195122384" TEXT="Dicke"/>
<node CREATED="1164195123212" ID="Freemind_Link_1171305586" MODIFIED="1164195125306" TEXT="Weitergabe"/>
</node>
</node>
<node CREATED="1164312882765" ID="Freemind_Link_534100457" MODIFIED="1164312898046" TEXT="waterCapazity (S)">
<node CREATED="1164312899593" ID="Freemind_Link_1979987904" MODIFIED="1164312922234" TEXT="Faktor zu demand"/>
</node>
</node>
<node CREATED="1164147640921" ID="Freemind_Link_1904136218" MODIFIED="1164147715000" TEXT="Systemvariabeln (nicht ver&#xe4;nderbar) - S">
<edge WIDTH="thin"/>
</node>
<node CREATED="1164147669484" ID="Freemind_Link_1993677434" MODIFIED="1164147722390" TEXT="Benutzervariablen (aus Datenbank) - B">
<edge WIDTH="thin"/>
</node>
<node CREATED="1164147741984" ID="Freemind_Link_189879990" MODIFIED="1164147773328" TEXT="Errechnete Werte - E"/>
</node>
<node CREATED="1164289829868" ID="Freemind_Link_1151195916" MODIFIED="1164289846620" POSITION="left" TEXT="Welt">
<node CREATED="1164289848999" ID="Freemind_Link_791740419" MODIFIED="1164289855547" TEXT="Koordinatensystem">
<node CREATED="1164289857338" ID="Freemind_Link_453192085" MODIFIED="1164297697049" TEXT="Ursprung x,y = linke obere Ecke"/>
</node>
<node CREATED="1164289885830" ID="Freemind_Link_1115279275" MODIFIED="1164289926752" TEXT="x-Achse positiv ansteigend = 0 / 360 &#xb0;"/>
<node CREATED="1164294160472" ID="Freemind_Link_1009204990" MODIFIED="1164294165566" TEXT="Intervalle">
<node CREATED="1164294166551" ID="Freemind_Link_177482224" MODIFIED="1164294180786" TEXT="Tag = 5 pro Stunde"/>
<node CREATED="1164294181599" ID="Freemind_Link_1085314673" MODIFIED="1164294189897" TEXT="Nacht = 2 pro Stunde"/>
</node>
</node>
<node CREATED="1164147109781" ID="Freemind_Link_1032073090" MODIFIED="1164147111734" POSITION="left" TEXT="Men&#xfc;">
<node CREATED="1164305085639" ID="Freemind_Link_1722019655" MODIFIED="1164305090780" TEXT="Pflegen">
<node CREATED="1164305115560" ID="Freemind_Link_1846413919" MODIFIED="1164305118545" TEXT="Gie&#xdf;en">
<node CREATED="1164305119857" ID="Freemind_Link_1089356944" MODIFIED="1164305133200" TEXT="5 verschieden Mengen"/>
<node CREATED="1164305219041" ID="Freemind_Link_1085064396" MODIFIED="1164305255915" TEXT="ScreenBaum.water += gew&#xe4;hlte Menge"/>
</node>
<node CREATED="1164305142044" ID="Freemind_Link_1648279100" MODIFIED="1164305149934" TEXT="Behandeln">
<node CREATED="1164305150934" ID="Freemind_Link_717410976" MODIFIED="1164305157496" TEXT="Selection des Elements">
<node CREATED="1164305158653" ID="Freemind_Link_1347826470" MODIFIED="1164305166231" TEXT="Beschneiden">
<node CREATED="1164305168793" ID="Freemind_Link_1145648087" MODIFIED="1164305191652" TEXT="Bis zu 2 Kinder an definierten Positionen"/>
</node>
</node>
</node>
</node>
<node CREATED="1164305092124" ID="Freemind_Link_1721265029" MODIFIED="1164305096280" TEXT="Optionen">
<node CREATED="1164305107404" ID="Freemind_Link_314316788" MODIFIED="1164305109529" TEXT="Neu"/>
<node CREATED="1164305097498" ID="Freemind_Link_1135824324" MODIFIED="1164305102123" TEXT="Speichern"/>
<node CREATED="1164305103295" ID="Freemind_Link_1161210349" MODIFIED="1164305105717" TEXT="Ende"/>
</node>
<node CREATED="1164147014609" ID="Freemind_Link_1447130742" MODIFIED="1164147514921" TEXT="keyEvent">
<node CREATED="1164147028062" ID="Freemind_Link_138495016" MODIFIED="1164147033968" TEXT="Select">
<node CREATED="1164310226375" ID="Freemind_Link_71625155" MODIFIED="1164310292375" TEXT="Aufruf MenuSelect"/>
</node>
<node CREATED="1164147035015" ID="Freemind_Link_1192010471" MODIFIED="1164147038406" TEXT="Nav">
<node CREATED="1164147039125" ID="Freemind_Link_1182207624" MODIFIED="1164147047937" TEXT="Weitergabe Eventlistener an unterobjekt"/>
<node CREATED="1164147084921" ID="Freemind_Link_1476080404" MODIFIED="1164147098937" TEXT="nicht m&#xf6;gliche Navigation ausblenden"/>
</node>
</node>
</node>
<node CREATED="1164297903386" ID="Freemind_Link_1268719815" MODIFIED="1165400599103" POSITION="right" TEXT="ScreenTree [extends Canvas]">
<node CREATED="1164297961357" ID="Freemind_Link_1782613429" MODIFIED="1164298004155" TEXT="Variabeln">
<node CREATED="1164298061938" ID="Freemind_Link_1822618820" MODIFIED="1164298096924" TEXT="log [Element]"/>
<node CREATED="1164298132472" ID="Freemind_Link_1695143980" MODIFIED="1164298163645" TEXT="logWaterRequest [short]"/>
<node CREATED="1164298188380" ID="Freemind_Link_1983122259" MODIFIED="1164298192802" TEXT="water [int]"/>
</node>
<node CREATED="1164297975717" ID="Freemind_Link_1772761822" MODIFIED="1164297982951" TEXT="Funktionen">
<node CREATED="1164298284162" ID="Freemind_Link_569932502" MODIFIED="1164298314959" TEXT="run()">
<node CREATED="1164298471993" ID="Freemind_Link_836866649" MODIFIED="1164298495102" TEXT="supply [short]">
<node CREATED="1164298495727" ID="Freemind_Link_165532451" MODIFIED="1164312544078" TEXT="supply = min(logWaterRequest,water);"/>
</node>
<node CREATED="1164298761404" ID="Freemind_Link_108429771" MODIFIED="1164298770670" TEXT="water-=supply;"/>
<node CREATED="1164298351335" ID="Freemind_Link_1378246702" MODIFIED="1164298455805" TEXT="logWaterRequest=log.grow(supply);"/>
</node>
<node CREATED="1164298338991" ID="Freemind_Link_1579112573" MODIFIED="1164298350288" TEXT="paint()">
<node CREATED="1164304985361" ID="Freemind_Link_152857901" MODIFIED="1164304994392" TEXT="log.draw(g)"/>
</node>
</node>
</node>
<node CREATED="1165400602059" ID="Freemind_Link_1563543788" MODIFIED="1165400878689" POSITION="right" TEXT="ScreenMenu"/>
</node>
</map>
