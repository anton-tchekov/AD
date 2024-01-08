#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>

static int readfile(const char *filename)
{
	
}

typedef struct
{
	uint16_t Prev;
	uint8_t Val;
} Entry;

static void output(int val)
{
	printf("%d\n", val);
}

int main(int argc, char **argv)
{
	int dict_size_bits, dict_size, pattern_len, dp, c, pattern;
	Entry *dict;

	if(argc != 3)
	{
		fprintf(stderr, "Usage: ./lzw filename dict-size-pot\n");
		return 1;
	}

	dict_size_bits = atoi(argv[2]);
	if(dict_size_bits < 9 || dict_size_bits > 15)
	{
		fprintf(stderr, "Invalid dictionary size\n");
		return 1;
	}

	dict_size = 1 << dict_size_bits;

	/* Initialize dictionary */
	dict = calloc(dict_size, sizeof(*dict));
	for(i = 0; i < 256; ++i)
	{
		dict[i].Value = i;
	}

	dp = 0;
	pattern = 0;
	c = 0;

	/* Process input file */
	while(p < len)
	{
		c = *p;
		if(dict_contains(pattern + c))
		{
			pattern[pattern_len++] = c;
		}
		else
		{
			if(dp < dict_size)
			{
				dict[dp].Prev = pattern;
				dict[dp].Val = c;
				++dp;
			}

			output(dp);
			pattern[0] = c;
			pattern_len = 1;
		}
	}

	free(dict);
	if(pattern_len > 0)
	{
		output(pattern);
	}

	return 0;
}


/*
     INITIALISIERE Mustertabelle MIT (<leeres Muster>,Zeichen) FÜR ALLE Zeichen
     last := lies_ersten_Code()
     Ausgabe(Muster VON last)
     SOLANGE NOCH Codes_verfügbar() WIEDERHOLE:
        next := lies_nächsten_Code()
        WENN next IN Mustertabelle DANN:
           FÜGE ( (Muster VON last), erstes_Zeichen_von(Muster VON next)) ZUR Mustertabelle HINZU
        SONST:
           FÜGE ( (Muster VON last), erstes_Zeichen_von(Muster VON last)) ZUR Mustertabelle HINZU
        Ausgabe(Muster VON next)
        last := next
*/
