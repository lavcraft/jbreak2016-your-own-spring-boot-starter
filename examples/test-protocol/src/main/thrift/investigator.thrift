namespace * example

enum TStatus {
    MR,
    MISS,
    MRS,
    MS
}

struct TName {
    1: required string firstName;
    2: required string secondName;
    3: optional TStatus status;
}

service TInvestigatingService {
    string whereIs(1: TName name);
}