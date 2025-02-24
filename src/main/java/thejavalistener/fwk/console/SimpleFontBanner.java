package thejavalistener.fwk.console;

import java.util.HashMap;
import java.util.Map;

public class SimpleFontBanner extends AbstractFontBanner
{
    private Map<Character, String[]> asciiMap;

    public SimpleFontBanner() {
        asciiMap = new HashMap<>();
        initializeAsciiMap();
    }

    @Override
    public String[][] getChar(char c) {
        String[] charLines = asciiMap.get(c);
        if( charLines==null )
        {
        	throw new RuntimeException("El carácter '"+c+"' no está actualemnte implementado en "+getClass().getName());
        }
        
        String[][] charRepresentation = new String[charLines.length][];
        for (int i = 0; i < charLines.length; i++) {
            charRepresentation[i] = new String[]{charLines[i]};
        }
        return charRepresentation;
    }

    private void initializeAsciiMap() {
        // Letras mayúsculas A-Z
        asciiMap.put('A', new String[]{
                "    ##    ",
                "   ####   ",
                "  ##  ##  ",
                " ##    ## ",
                " ######## ",
                " ##    ## ",
                " ##    ## ",
                " ##    ## "
        });
        asciiMap.put('B', new String[]{
                " #######  ",
                " ##    ## ",
                " ##    ## ",
                " #######  ",
                " ##    ## ",
                " ##    ## ",
                " ##    ## ",
                " #######  "
        });
        asciiMap.put('C', new String[]{
                "  ######  ",
                " ##    ## ",
                " ##       ",
                " ##       ",
                " ##       ",
                " ##       ",
                " ##    ## ",
                "  ######  "
        });
        asciiMap.put('D', new String[]{
                " #######  ",
                " ##    ## ",
                " ##    ## ",
                " ##    ## ",
                " ##    ## ",
                " ##    ## ",
                " ##    ## ",
                " #######  "
        });
        asciiMap.put('E', new String[]{
                " ######## ",
                " ##       ",
                " ##       ",
                " ######   ",
                " ##       ",
                " ##       ",
                " ##       ",
                " ######## "
        });
        asciiMap.put('F', new String[]{
                " ######## ",
                " ##       ",
                " ##       ",
                " ######   ",
                " ##       ",
                " ##       ",
                " ##       ",
                " ##       "
        });
        asciiMap.put('G', new String[]{
                "  ######  ",
                " ##    ## ",
                " ##       ",
                " ##       ",
                " ##   ### ",
                " ##    ## ",
                " ##    ## ",
                "  ######  "
        });
        asciiMap.put('H', new String[]{
                " ##    ## ",
                " ##    ## ",
                " ##    ## ",
                " ######## ",
                " ##    ## ",
                " ##    ## ",
                " ##    ## ",
                " ##    ## "
        });
        asciiMap.put('I', new String[]{
                "   ####   ",
                "    ##    ",
                "    ##    ",
                "    ##    ",
                "    ##    ",
                "    ##    ",
                "    ##    ",
                "   ####   "
        });
        asciiMap.put('J', new String[]{
                "      ##  ",
                "      ##  ",
                "      ##  ",
                "      ##  ",
                " ##   ##  ",
                " ##   ##  ",
                " ##   ##  ",
                "  #####   "
        });
        asciiMap.put('K', new String[]{
                " ##    ## ",
                " ##   ##  ",
                " ##  ##   ",
                " #####    ",
                " ##  ##   ",
                " ##   ##  ",
                " ##    ## ",
                " ##     ##"
        });
        asciiMap.put('L', new String[]{
                " ##       ",
                " ##       ",
                " ##       ",
                " ##       ",
                " ##       ",
                " ##       ",
                " ##       ",
                " ######## "
        });
        asciiMap.put('M', new String[]{
                " ##    ## ",
                " ###  ### ",
                " ## ## ## ",
                " ## ## ## ",
                " ##    ## ",
                " ##    ## ",
                " ##    ## ",
                " ##    ## "
        });
        asciiMap.put('N', new String[]{
                " ##    ## ",
                " ###   ## ",
                " ####  ## ",
                " ## ## ## ",
                " ##  #### ",
                " ##   ### ",
                " ##    ## ",
                " ##    ## "
        });
        asciiMap.put('O', new String[]{
                "  ######  ",
                " ##    ## ",
                " ##    ## ",
                " ##    ## ",
                " ##    ## ",
                " ##    ## ",
                " ##    ## ",
                "  ######  "
        });
        asciiMap.put('P', new String[]{
                " #######  ",
                " ##    ## ",
                " ##    ## ",
                " #######  ",
                " ##       ",
                " ##       ",
                " ##       ",
                " ##       "
        });
        asciiMap.put('Q', new String[]{
                "  ######  ",
                " ##    ## ",
                " ##    ## ",
                " ##    ## ",
                " ##  ## ##",
                " ##    ## ",
                " ##   ##  ",
                "  ##### ##"
        });
        asciiMap.put('R', new String[]{
                " #######  ",
                " ##    ## ",
                " ##    ## ",
                " #######  ",
                " ##   ##  ",
                " ##    ## ",
                " ##    ## ",
                " ##     ##"
        });
        asciiMap.put('S', new String[]{
                "  ####### ",
                " ##     ##",
                " ##       ",
                "  ######  ",
                "       ## ",
                " ##    ## ",
                " ##     ##",
                "  ####### "
        });
        asciiMap.put('T', new String[]{
                " ######## ",
                "    ##    ",
                "    ##    ",
                "    ##    ",
                "    ##    ",
                "    ##    ",
                "    ##    ",
                "    ##    "
        });
        asciiMap.put('U', new String[]{
                " ##    ## ",
                " ##    ## ",
                " ##    ## ",
                " ##    ## ",
                " ##    ## ",
                " ##    ## ",
                " ##    ## ",
                "  ######  "
        });
        asciiMap.put('V', new String[]{
                " ##    ## ",
                " ##    ## ",
                " ##    ## ",
                " ##    ## ",
                " ##    ## ",
                "  ##  ##  ",
                "   ####   ",
                "    ##    "
        });
        asciiMap.put('W', new String[]{
                " ##    ## ",
                " ##    ## ",
                " ##    ## ",
                " ## ## ## ",
                " ## ## ## ",
                " ## ## ## ",
                " ###  ### ",
                " ##    ## "
        });
        asciiMap.put('X', new String[]{
                " ##    ## ",
                "  ##  ##  ",
                "   ####   ",
                "    ##    ",
                "   ####   ",
                "  ##  ##  ",
                " ##    ## ",
                " ##    ## "
        });
        asciiMap.put('Y', new String[]{
                " ##    ## ",
                " ##    ## ",
                "  ##  ##  ",
                "   ####   ",
                "    ##    ",
                "    ##    ",
                "    ##    ",
                "    ##    "
        });
        asciiMap.put('Z', new String[]{
                " ######## ",
                "      ##  ",
                "     ##   ",
                "    ##    ",
                "   ##     ",
                "  ##      ",
                " ##       ",
                " ######## "
        });
        asciiMap.put('Ñ', new String[]{
                " ##   ## ",
                " ###  ## ",
                " ## # ## ",
                " ##  ### ",
                " ##   ## ",
                " ##   ## ",
                " ##   ## ",
                " ##   ## "
        });
        
        asciiMap.put(' ', new String[]{
                "       ",
                "       ",
                "       ",
                "       ",
                "       ",
                "       ",
                "       ",
                "       "
        });

        
        asciiMap.put('a', new String[]{
                "         ",
                "         ",
                "  #####  ",
                "       ##",
                "  #######",
                " ##    ##",
                " ##    ##",
                "  #######"
        });
        asciiMap.put('b', new String[]{
                "  ##      ",
                "  ##      ",
                "  ##      ",
                "  #####   ",
                "  ##  ##  ",
                "  ##   ## ",
                "  ##   ## ",
                "  #####   "
        });
        asciiMap.put('c', new String[]{
                "         ",
                "         ",
                "  ###### ",
                " ##    ##",
                " ##      ",
                " ##      ",
                " ##    ##",
                "  ###### "
        });
        asciiMap.put('d', new String[]{
                "       ##",
                "       ##",
                "       ##",
                "  ###### ",
                " ##   ## ",
                " ##    ##",
                " ##    ##",
                "  #######"
        });
        asciiMap.put('e', new String[]{
                "         ",
                "         ",
                "  #####  ",
                " ##   ## ",
                " ####### ",
                " ##      ",
                " ##      ",
                "  #####  "
        });
        asciiMap.put('f', new String[]{
                "     ### ",
                "    ##   ",
                "   ##    ",
                " ####### ",
                "   ##    ",
                "   ##    ",
                "   ##    ",
                "   ##    "
        });
        asciiMap.put('g', new String[]{
                "         ",
                "         ",
                "  #######",
                " ##    ##",
                " ##    ##",
                "  #######",
                "       ##",
                "  ###### "
        });
        asciiMap.put('h', new String[]{
                "  ##      ",
                "  ##      ",
                "  ##      ",
                "  #####   ",
                "  ##  ##  ",
                "  ##   ## ",
                "  ##   ## ",
                "  ##   ## "
        });
        asciiMap.put('i', new String[]{
                "    ##    ",
                "          ",
                "  ####    ",
                "    ##    ",
                "    ##    ",
                "    ##    ",
                "    ##    ",
                "    ##    "
        });
        asciiMap.put('j', new String[]{
                "      ## ",
                "         ",
                "   ######",
                "      ## ",
                "      ## ",
                "      ## ",
                "      ## ",
                " ######  "
        });
        asciiMap.put('k', new String[]{
                "  ##      ",
                "  ##      ",
                "  ##      ",
                "  ##  ##  ",
                "  ## ##   ",
                "  #####   ",
                "  ##  ##  ",
                "  ##   ## "
        });
        asciiMap.put('l', new String[]{
                "  ####    ",
                "    ##    ",
                "    ##    ",
                "    ##    ",
                "    ##    ",
                "    ##    ",
                "    ##    ",
                "  ####### "
        });
        asciiMap.put('m', new String[]{
                "         ",
                "         ",
                " ## ## ##",
                " ########",
                " ##  # ##",
                " ##  # ##",
                " ##  # ##",
                " ##  # ##"
        });
        asciiMap.put('n', new String[]{
                "         ",
                "         ",
                "  #####  ",
                "  ##  ## ",
                "  ##   ##",
                "  ##   ##",
                "  ##   ##",
                "  ##   ##"
        });
        
        asciiMap.put('ñ', new String[]{
                "  ##### ",
                "         ",
                "  #####  ",
                "  ##  ## ",
                "  ##   ##",
                "  ##   ##",
                "  ##   ##",
                "  ##   ##"
        	});
        
        
        
        asciiMap.put('o', new String[]{
                "         ",
                "         ",
                "  ###### ",
                " ##    ##",
                " ##    ##",
                " ##    ##",
                " ##    ##",
                "  ###### "
        });
        asciiMap.put('p', new String[]{
                "         ",
                "         ",
                " ######  ",
                " ##   ## ",
                " ##    ##",
                " ##    ##",
                " ####### ",
                " ##      "
        });
        asciiMap.put('q', new String[]{
                "         ",
                "         ",
                "  #######",
                " ##    ##",
                " ##    ##",
                " ##   ###",
                "  #######",
                "      ## "
        });
        asciiMap.put('r', new String[]{
                "         ",
                "         ",
                "  ##  ## ",
                "  ## ##  ",
                "  ####   ",
                "  ##     ",
                "  ##     ",
                "  ##     "
        });
        asciiMap.put('s', new String[]{
                "         ",
                "         ",
                "  #######",
                "  ##     ",
                "   ####  ",
                "      ## ",
                "  ##    #",
                "  ###### "
        });
        asciiMap.put('t', new String[]{
                "         ",
                "   ##    ",
                "   ##    ",
                " ####### ",
                "   ##    ",
                "   ##    ",
                "   ##    ",
                "    #####"
        });
        asciiMap.put('u', new String[]{
                "         ",
                "         ",
                " ##   ## ",
                " ##   ## ",
                " ##   ## ",
                " ##   ## ",
                " ##  ### ",
                "  ##### #"
        });
        asciiMap.put('v', new String[]{
                "         ",
                "         ",
                " ##    ##",
                " ##    ##",
                "  ##  ## ",
                "   ####  ",
                "    ##   ",
                "    ##   "
        });
        asciiMap.put('w', new String[]{
                "         ",
                "         ",
                " ##    ##",
                " ##    ##",
                " ## ## ##",
                " ## ## ##",
                " ## ## ##",
                " ##    ##"
        });
        asciiMap.put('x', new String[]{
                "         ",
                "         ",
                " ##   ## ",
                "  ## ##  ",
                "   ##    ",
                "  ## ##  ",
                " ##   ## ",
                " ##   ## "
        });
        asciiMap.put('y', new String[]{
                "         ",
                "         ",
                " ##    ##",
                "  ##  ## ",
                "   ####  ",
                "    ##   ",
                "    ##   ",
                "    ##   "
        });
        asciiMap.put('z', new String[]{
                "         ",
                "         ",
                " ########",
                "      ## ",
                "     ##  ",
                "    ##   ",
                "  ##     ",
                " ########"
        });

        asciiMap.put('0', new String[]{
        	    "  ######  ",
        	    " ##    ## ",
        	    " ##    ## ",
        	    " ##    ## ",
        	    " ##    ## ",
        	    " ##    ## ",
        	    " ##    ## ",
        	    "  ######  "
        	});
        	asciiMap.put('1', new String[]{
        	    "    ##    ",
        	    "   ###    ",
        	    "  ####    ",
        	    "    ##    ",
        	    "    ##    ",
        	    "    ##    ",
        	    "    ##    ",
        	    "  ######  "
        	});
        	asciiMap.put('2', new String[]{
        	    "  ######  ",
        	    " ##    ## ",
        	    "       ## ",
        	    "     ###  ",
        	    "   ###    ",
        	    "  ##      ",
        	    " ##       ",
        	    " ######## "
        	});
        	asciiMap.put('3', new String[]{
        	    "  ######  ",
        	    " ##    ## ",
        	    "       ## ",
        	    "   #####  ",
        	    "       ## ",
        	    "       ## ",
        	    " ##    ## ",
        	    "  ######  "
        	});
        	asciiMap.put('4', new String[]{
        	    "       ## ",
        	    "     #### ",
        	    "    ##### ",
        	    "   ## ##  ",
        	    "  ##  ##  ",
        	    " ######## ",
        	    "      ##  ",
        	    "      ##  "
        	});
        	asciiMap.put('5', new String[]{
        	    " ######## ",
        	    " ##       ",
        	    " ##       ",
        	    " #######  ",
        	    "       ## ",
        	    "       ## ",
        	    " ##    ## ",
        	    "  ######  "
        	});
        	asciiMap.put('6', new String[]{
        	    "  ######  ",
        	    " ##    ## ",
        	    " ##       ",
        	    " #######  ",
        	    " ##    ## ",
        	    " ##    ## ",
        	    " ##    ## ",
        	    "  ######  "
        	});
        	asciiMap.put('7', new String[]{
        	    " ######## ",
        	    " ##    ## ",
        	    "      ##  ",
        	    "     ##   ",
        	    "    ##    ",
        	    "    ##    ",
        	    "    ##    ",
        	    "    ##    "
        	});
        	asciiMap.put('8', new String[]{
        	    "  ######  ",
        	    " ##    ## ",
        	    " ##    ## ",
        	    "  ######  ",
        	    " ##    ## ",
        	    " ##    ## ",
        	    " ##    ## ",
        	    "  ######  "
        	});
        	asciiMap.put('9', new String[]{
        	    "  ######  ",
        	    " ##    ## ",
        	    " ##    ## ",
        	    "  ####### ",
        	    "       ## ",
        	    "       ## ",
        	    " ##    ## ",
        	    "  ######  "
        	});

        	asciiMap.put('?', new String[]{
        		    "  #####  ",
        		    " ##   ## ",
        		    "      ## ",
        		    "     ##  ",
        		    "    ##   ",
        		    "         ",
        		    "    ##   ",
        		    "    ##   "
        		});
        		asciiMap.put('¿', new String[]{
        		    "   ##    ",
        		    "   ##    ",
        		    "         ",
        		    "   ##    ",
        		    "  ##     ",
        		    " ##      ",
        		    " ##   ## ",
        		    "  #####  "
        		});
        		asciiMap.put('!', new String[]{
        		    "   ##    ",
        		    "   ##    ",
        		    "   ##    ",
        		    "   ##    ",
        		    "   ##    ",
        		    "         ",
        		    "   ##    ",
        		    "   ##    "
        		});
        		asciiMap.put('¡', new String[]{
        		    "    ##   ",
        		    "    ##   ",
        		    "         ",
        		    "    ##   ",
        		    "    ##   ",
        		    "    ##   ",
        		    "    ##   ",
        		    "    ##   "
        		});

        		asciiMap.put('-', new String[]{
        			    "        ",
        			    "        ",
        			    "        ",
        			    " ###### ",
        			    "        ",
        			    "        ",
        			    "        ",
        			    "        "
        			});
        			asciiMap.put('_', new String[]{
        			    "        ",
        			    "        ",
        			    "        ",
        			    "        ",
        			    "        ",
        			    "        ",
        			    "        ",
        			    " ###### "
        			});
        			asciiMap.put('+', new String[]{
        			    "        ",
        			    "   ##   ",
        			    "   ##   ",
        			    " ###### ",
        			    "   ##   ",
        			    "   ##   ",
        			    "        ",
        			    "        "
        			});
        			asciiMap.put('*', new String[]{
        			    "        ",
        			    "  #  #  ",
        			    "   ##   ",
        			    " ###### ",
        			    "   ##   ",
        			    "  #  #  ",
        			    "        ",
        			    "        "
        			});
        			asciiMap.put('/', new String[]{
        			    "       #",
        			    "      ##",
        			    "     ## ",
        			    "    ##  ",
        			    "   ##   ",
        			    "  ##    ",
        			    " ##     ",
        			    "#       "
        			});

        			asciiMap.put(',', new String[]{
        				    "        ",
        				    "        ",
        				    "        ",
        				    "        ",
        				    "        ",
        				    "   ##   ",
        				    "   ##   ",
        				    "  ##    "
        				});
        				asciiMap.put('.', new String[]{
        				    "        ",
        				    "        ",
        				    "        ",
        				    "        ",
        				    "        ",
        				    "   ##   ",
        				    "   ##   ",
        				    "        "
        				});
        				asciiMap.put('(', new String[]{
        					    "    ##  ",
        					    "   ##   ",
        					    "  ##    ",
        					    "  ##    ",
        					    "  ##    ",
        					    "  ##    ",
        					    "   ##   ",
        					    "    ##  "
        					});
        					asciiMap.put(')', new String[]{
        					    "  ##    ",
        					    "   ##   ",
        					    "    ##  ",
        					    "    ##  ",
        					    "    ##  ",
        					    "    ##  ",
        					    "   ##   ",
        					    "  ##    "
        					});
        					asciiMap.put('#', new String[]{
        					    "   ## ##   ",
        					    "   ## ##   ",
        					    " ######### ",
        					    "   ## ##   ",
        					    " ######### ",
        					    "   ## ##   ",
        					    "   ## ##   ",
        					    "   ## ##   "
        					});
        					asciiMap.put('%', new String[]{
        					    "  ##     ##  ",
        					    " ##     ##   ",
        					    "      ##     ",
        					    "     ##      ",
        					    "    ##       ",
        					    "   ##        ",
        					    " ##     ##   ",
        					    "##     ##    "
        					});
        					asciiMap.put('=', new String[]{
        					    "          ",
        					    "          ",
        					    " ######## ",
        					    "          ",
        					    "          ",
        					    " ######## ",
        					    "          ",
        					    "          "
        					});
        					asciiMap.put('\'', new String[]{
        					    "  ##  ",
        					    "  ##  ",
        					    "  ##  ",
        					    "      ",
        					    "      ",
        					    "      ",
        					    "      ",
        					    "      "
        					});
        					asciiMap.put('[', new String[]{
        					    "  ####  ",
        					    "  ##    ",
        					    "  ##    ",
        					    "  ##    ",
        					    "  ##    ",
        					    "  ##    ",
        					    "  ##    ",
        					    "  ####  "
        					});
        					asciiMap.put(']', new String[]{
        					    "  ####  ",
        					    "    ##  ",
        					    "    ##  ",
        					    "    ##  ",
        					    "    ##  ",
        					    "    ##  ",
        					    "    ##  ",
        					    "  ####  "
        					});
        					asciiMap.put('{', new String[]{
        					    "     ### ",
        					    "    ##   ",
        					    "    ##   ",
        					    "  ###    ",
        					    "    ##   ",
        					    "    ##   ",
        					    "    ##   ",
        					    "     ### "
        					});
        					asciiMap.put('}', new String[]{
        					    " ###     ",
        					    "   ##    ",
        					    "   ##    ",
        					    "    ###  ",
        					    "   ##    ",
        					    "   ##    ",
        					    "   ##    ",
        					    " ###     "
        					});
        					asciiMap.put('$', new String[]{
        					    "    ##    ",
        					    "  ####### ",
        					    " ##  ##   ",
        					    "  ######  ",
        					    "    ## ## ",
        					    "    ## ## ",
        					    " #######  ",
        					    "    ##    "
        					});
        					asciiMap.put('\\', new String[]{
        					    "##       ",
        					    " ##      ",
        					    "  ##     ",
        					    "   ##    ",
        					    "    ##   ",
        					    "     ##  ",
        					    "      ## ",
        					    "       ##"
        					});

        	
        					asciiMap.put('á', asciiMap.get('a'));
        					asciiMap.put('é', asciiMap.get('e'));
        					asciiMap.put('í', asciiMap.get('i'));
        					asciiMap.put('ó', asciiMap.get('o'));
        					asciiMap.put('ú', asciiMap.get('u'));
        					asciiMap.put('Á', asciiMap.get('A'));
        					asciiMap.put('É', asciiMap.get('E'));
        					asciiMap.put('Í', asciiMap.get('I'));
        					asciiMap.put('Ó', asciiMap.get('O'));
        					asciiMap.put('Ú', asciiMap.get('U'));
    }
}



        		 
        		 
        		 
        		 
        		 
        		 
        		 
        		 
         
                 
        		 
        		 
        		 
        		 
        		 
                 
                 
                 
                 
                 
                 