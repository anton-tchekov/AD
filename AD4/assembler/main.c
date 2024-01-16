#include <stdio.h>
#include <stdint.h>

#define DICT_BITS 12
#define DICT_SIZE   (1 << DICT_BITS)

struct dict
{
	int64_t key;
	uint64_t hash;
};

#define TABLE_SIZE (64 * 1024)

struct dict table[TABLE_SIZE];

uint64_t myhash(const char *str)
{
	uint64_t hash = 5381;
	int c;
	while(c = *str++)
	{
		hash = ((hash << 5) + hash) + c;
	}

	return hash;
}

void dict_put(const char *s, int64_t val)
{
	printf("save %s -> %d\n", s, val);

	uint64_t h = myhash(s);
	uint64_t i = h & (TABLE_SIZE - 1);
	printf("i = %lld\n", i);
	for(;;)
	{
		if(table[i].hash == 0)
		{
			table[i].hash = h;
			table[i].key = val;
			break;
		}

		++i;
		i &= TABLE_SIZE - 1;
	}
}

int64_t dict_get(const char *s)
{
	uint64_t h = myhash(s);
	uint64_t i = h & (TABLE_SIZE - 1);
	for(;;)
	{
		if(table[i].hash == 0)
		{
			return -1;
		}
		else if(table[i].hash == h)
		{
			return table[i].key;
		}

		++i;
		i &= TABLE_SIZE - 1;
	}
}

int dict_contains(const char *s)
{
	return dict_get(s) > 0;
}

static char buf[32];
void printn(uint64_t n)
{
	char *s = buf + 31;
	*s = '\0';
	while(n)
	{
		--s;
		*s = (n % 10) + '0';
		n /= 10;
	}

	puts(s);
}

int main(void)
{
	dict_put("Hello", 42);
	dict_put("tst", 69);
	dict_put("bla", 777);

	printf("tst -> %d\n", dict_get("tst"));
	printf("bla -> %d\n", dict_get("bla"));
	printf("Hello -> %d\n", dict_get("Hello"));

	printf("irgendwas -> %d\n", dict_get("irgendwas"));

	/*int dict_entries;
	for(dict_entries = 0; dict_entries < BYTE_STATES; ++dict_entries)
	{
		dict_put(Character.toString(dict_entries), dict_entries);
	}

	String word = "";
	write_bits(out, 4, DICT_BITS);
	for(;;)
	{
		int cur_byte = in_read();
		if(cur_byte < 0)
		{
			break;
		}

		String next = word + (char)cur_byte;
		if(!dict_contains(next))
		{
			write_bits(out, DICT_BITS, dict_get(word));
			if(dict_entries < dict_size)
			{
				dict_put(next, dict_entries++);
			}
			next = "" + (char)cur_byte;
		}
		word = next;
	}

	if(word.length() > 0)
	{
		write_bits(out, DICT_BITS, dict_get(word));
	}

	output_end(out);*/
	return 0;
}
