#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>

static uint8_t *out;
static size_t bit;

static void writeheader(int used, uint8_t *freq)
{
	*out++ = 'H';
	*out++ = 'U';
	*out++ = 'F';
	*out++ = used;
	for(i = 0; i < used; ++i)
	{
		*out++ = freq[i];
	}
}

static void writeone(void)
{
	out[bit >> 3] |= (1 << (7 - (bit & 0x7)));
	++bit;
}

static void writebits(int freq, int minfreq)
{
	int i;

	/* write freq ones */
	for(i = 0; i < freq; ++i)
	{
		writeone();
	}

	if(freq != minfreq)
	{
		/* write zero */
		++bit;
	}
}

int compar(void *a, void *b)
{
}

int main(int argc, char **argv)
{
	int used;
	uint8_t *p, *end;
	size_t freq[256];
	uint8_t all[256];
	memset(freq, 0, sizeof(freq));

	/* Count character frequency */
	for(; p < end; ++p)
	{
		++freq[*p];
	}

	used = 0;
	for(int i = 0; i < 256; ++i)
	{
		if(!freq[i])
		{
			++used;
		}
	}

	for(int i = 0; i < 256; ++i)
	{
		all[i] = i;
	}

	qsort(freq, 256, 1, compar);
	for(; p < end; ++p)
	{
		++freq[*p];
		writebits();
	}

	return 0;
}
