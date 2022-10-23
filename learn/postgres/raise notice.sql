-- https://www.jianshu.com/p/59e72e2f8866


do $$
declare
    a integer := 10;
    b integer := 20;
begin
    if a>b then
        raise notice 'a is greater then b';
    end if;

    if a<b then
        raise notice 'a is less then b';
    end if;

    if a=b then
        raise notice 'a is equal to b ';
    end if;
end; $$