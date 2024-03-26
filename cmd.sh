set -eu

SCRIPT_DIR=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" &>/dev/null && pwd)

check_arg() {
    local val_arg="$1"

    for arg in "${@:2}"; do
        if [ "$arg" = "$val_arg" ]; then
            return 0 # Success: found
        fi
    done

    return 1 # Failure: not found
}

pre-commit install

if check_arg "--jdk" "$@"; then
    echo "---"
    sdk install java 22-open
    exit 0
fi

if check_arg "--build" "$@"; then
    echo "---"
    source .env.default
    ./mvnw clean package
    ls -la ./target
    exit 0
fi

if check_arg "--run" "$@"; then
    echo "---"
    source .env.default
    ./mvnw -DskipTests spring-boot:run
    exit 0
fi

if check_arg "--format" "$@"; then
    echo "---"
    pre-commit run --all-files
fi
