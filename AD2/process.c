#include <stdio.h>

int values[] =
{
#include "out.txt"
};

static int out_mov[24][250];

static int out_comp[24][250];

int main(void)
{
	int N = sizeof(values) / sizeof(*values);

	int j = 0;
	for(int i = 0; i < N; i += 5)
	{
		int *v = &values[i];

		int size = v[0];
		int list_type = v[1];
		int sort_type = v[2];
		int compares = v[3];
		int moves = v[4];

		out_mov[list_type][j] = moves;
		out_compare[list_type][j] = compares;
		++j;
	}

	char *ltn[24] =
	{
		"rand",
		"ord",
		"rev",
		"part",
	};

	char *sortn[24] =
	{
		"rand",
		"ord",
		"rev",
		"part",
	};

	for(int i = 0; i < 24; ++i)
	{
		printf("mov_%s", names[i]);
		for(int j = 0; j < N; ++j)
		{

		}
	}

	return 0;
}
